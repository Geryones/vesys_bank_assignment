package bank.client;

import bank.*;
import bank.command.*;
import bank.local.Driver;
import bank.server.ServerAccount;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Geryones on 25/02/2019.
 */
public class ClientBank implements Bank{
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private Socket s;
    private final Map<String, ClientAccount> accounts = new HashMap<>();



    public ClientBank(Socket s) throws IOException {

       this.s = s;
        out = new ObjectOutputStream(s.getOutputStream());
        in = new ObjectInputStream(s.getInputStream());
    }


    @Override public String createAccount(String owner) throws IOException {
        Request createAccount = new CreateAccount(owner);
        out.writeObject(createAccount);
        out.flush();
        String number = null;
        try {
            Object obj = in.readObject();
            if (obj instanceof CreateAccount) {
                number = ((CreateAccount) obj).getNumber();
            } else {
                throw new IOException("Expected Create Account");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return number;
    }

    @Override public boolean closeAccount(String number) throws IOException {
       Request closeAccount = new CloseAccount(number);
       out.writeObject(closeAccount);
       out.flush();
       Object obj = null;
        try {
            obj = in.readObject();
            if (!(obj instanceof CloseAccount)){
                throw new ClassNotFoundException("expected CloseAccount");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ((CloseAccount)obj).isRemoved();
    }

    @Override public Set<String> getAccountNumbers() throws IOException {
        Request getAccountNumbers = new GetAccountNumbers();
        out.writeObject(getAccountNumbers);
        out.flush();
        Object obj = null;
        try {
            obj = in.readObject();
            if (obj instanceof Exceptions){
                ((Exceptions) obj).getErrors().get(0).printStackTrace();
            }
            if (!(obj instanceof GetAccountNumbers)){
                throw new ClassNotFoundException("Expected GetAccountNumbers ");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("got: "+obj.getClass().getName());
            e.printStackTrace();

        }
        System.out.println();

        return ((GetAccountNumbers)obj).getAccountNumbers();
    }

    @Override public Account getAccount(String number) throws IOException {
        if (accounts.containsKey(number)) {

        }else {

            Request getAccount = new GetAccount(number);
            out.writeObject(getAccount);
            out.flush();
            Object obj = null;
            try {
                obj = in.readObject();
                if (!(obj instanceof GetAccount)) {
                    throw new ClassNotFoundException("expected GetAccount");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

           ClientAccount account = new ClientAccount(number,((GetAccount) obj).getOwner(), out, in);
            accounts.put(number, account);
        }
        return accounts.get(number);
    }

    @Override public void transfer(Account from, Account to, double amount)
            throws IOException, IllegalArgumentException, OverdrawException, InactiveException {

        from.withdraw(amount);

       try{
           to.deposit(amount);
       }catch(InactiveException e) {
           from.deposit(amount);
           e.printStackTrace();
       }

    }

    public void disconnect() throws IOException {
        in.close();
        out.close();
    }


}
