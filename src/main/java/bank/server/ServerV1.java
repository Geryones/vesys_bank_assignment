package bank.server;

import bank.InactiveException;
import bank.OverdrawException;
import bank.Request;
import bank.command.*;
import com.sun.javafx.stage.WindowEventDispatcher;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerV1 {
    public static void main(String[] args) {
        int port = 1234;

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Startet my Server V1 on port " + port);
            ExecutorService pool = Executors.newCachedThreadPool();
            while (true) {
                Socket s = server.accept();
                pool.execute(new Handler(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //daraus wird der bank server gebaut
    private static class Handler implements Runnable {
        private final Socket s;
        ServerBank serverBank;
        Request errors;
        private final ObjectOutputStream out;
        private final ObjectInputStream in;


        Handler(Socket socket) throws IOException {
            this.s = socket;
            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());
        }

        public void run() {
            try {

                while (in != null && !"".equals(in)) {
                    Object obj = in.readObject();

                    if (obj instanceof CreateAccount){
                        ((CreateAccount) obj).setNumber(
                                serverBank.getInstance().createAccount(((CreateAccount) obj).getOwner()));
                        out.writeObject(obj);
                        out.flush();
                    }else if (obj instanceof GetAccountNumbers){
                        ((GetAccountNumbers) obj).setAccountNumbers(
                                serverBank.getInstance().getAccountNumbers()
                        );
                        out.writeObject(obj);
                        out.flush();
                    }else if (obj instanceof GetAccount){
                        Request getAccount = new GetAccount(serverBank.getInstance().getAccount(
                                ((GetAccount) obj).getNumber()));

                        out.writeObject(getAccount);
                        out.flush();
                    }else if (obj instanceof CloseAccount) {
                        ((CloseAccount) obj).setRemoved(serverBank.getInstance().closeAccount(
                                ((CloseAccount) obj).getNumber()));
                        out.writeObject(obj);
                        out.flush();
                    }else if(obj instanceof IsActive){
                        ((IsActive)obj).setActive(
                                serverBank.getInstance().getAccount(
                                        ((IsActive) obj).getNumber()).isActive());

                    }else if(obj instanceof Deposit){
                        errors = new Exceptions();
                        try {
                            serverBank.getInstance().getAccount(
                                    ((Deposit)obj).getNumber()).deposit(((Deposit)obj).getAmount());
                        } catch (InactiveException | IllegalArgumentException e) {

                            ((Exceptions) errors).setError(e);
                            out.writeObject(errors);
                        }

                    }else if(obj instanceof Withdraw) {
                        errors = new Exceptions();
                        try {
                            serverBank.getInstance().getAccount(
                                    ((Withdraw)obj).getNumber()).withdraw(
                                            ((Withdraw) obj).getAmount());
                        } catch (OverdrawException e) {
                            ((Exceptions)errors).setError(e);
                            out.writeObject(errors);
                        } catch (InactiveException e) {
                            ((Exceptions)errors).setError(e);

                        }

                    }else if(obj instanceof GetBalance){
                        ((GetBalance)obj).setBalance(
                                serverBank.getInstance().getAccount(
                                        ((GetBalance)obj).getNumber()).getBalance());

                        out.writeObject(obj);
                        out.flush();
                    }else { //Das ist der Default, falls keine Klasse vorhanden ist
                        System.out.println("Klasse kann nicht zugeordnet werden");
                        System.out.println(obj.getClass().getName());
                    }

                }
                System.out.println("done serving " + s);
                s.close();
                in.close();
                out.close();
            } catch (IOException e) {
                System.err.println(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

