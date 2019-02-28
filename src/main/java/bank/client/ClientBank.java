package bank.client;

import bank.*;
import bank.command.CreateAccount;
import bank.command.GetAccount;
import bank.command.GetAccountNumbers;
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
    private final Map<String, ServerAccount> accounts = new HashMap<>();



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
                number = ((CreateAccount) obj).getAccountNumber();
            } else {
                throw new IOException("Expected Create Account");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return number;
    }

    @Override public boolean closeAccount(String number) throws IOException {
        return false;
    }

    @Override public Set<String> getAccountNumbers() throws IOException {
        Request getAccountNumbers = new GetAccountNumbers();
        out.writeObject(getAccountNumbers);
        out.flush();
        Object obj = null;
        try {
            obj = in.readObject();
            if (!(obj instanceof GetAccountNumbers)){
                throw new ClassNotFoundException("Expected GetAccountNumbers ");
            }
        } catch (ClassNotFoundException e) {
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

            ServerAccount account = new ServerAccount(((GetAccount) obj).getOwner(), number);
            accounts.put(number, account);
        }
        return accounts.get(number);
    }

    @Override public void transfer(Account a, Account b, double amount)
            throws IOException, IllegalArgumentException, OverdrawException, InactiveException {

    }

    public void disconnect() throws IOException {
        in.close();
        out.close();
    }


}
