package bank.server;

import bank.Request;
import bank.command.CreateAccount;
import bank.command.GetAccountNumbers;

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
        private ServerBank serverBank;
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
                        ((CreateAccount) obj).setAccountNumber(
                                serverBank.createAccount(((CreateAccount) obj).getOwner()));
                        out.writeObject(obj);
                    }else if (obj instanceof GetAccountNumbers){
                        ((GetAccountNumbers) obj).setAccountNumbers(
                                serverBank.getAccountNumbers()
                        );
                        out.writeObject(obj);
                    }

                }
                System.out.println("done serving " + s);
                s.close();
                in.close();
                out.close();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println(e);
            }
        }
    }
}

