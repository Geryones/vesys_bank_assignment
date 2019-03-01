package bank.client;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;
import bank.Request;
import bank.command.*;
import org.hamcrest.core.Is;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Geryones on 25/02/2019.
 */
public class ClientAccount implements Account {
    private String number;
    private String owner;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public ClientAccount(String number, String owner, ObjectOutputStream out, ObjectInputStream in) {
        this.number = number;
        this.owner = owner;
        this.out = out;
        this.in = in;
    }

    @Override public String getNumber() throws IOException {
        return number;
    }

    @Override public String getOwner() throws IOException {
        return owner;
    }

    @Override public boolean isActive() throws IOException {
        Request isActive = new IsActive(number);
        out.writeObject(isActive);
        out.flush();
        Object obj = null;

        try {
            obj = in.readObject();
            if(!(obj instanceof IsActive)){
                throw new ClassNotFoundException("expected IsActive");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ((IsActive)obj).isActive();
    }

    @Override public void deposit(double amount) throws IOException, IllegalArgumentException, InactiveException {
            Request deposit = new Deposit(number, amount);
            out.writeObject(deposit);


    }

    @Override public void withdraw(double amount)
            throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
            Request withdraw = new Withdraw(number, amount);
            out.writeObject(withdraw);

    }

    @Override public double getBalance() throws IOException {
        Request getBalance = new GetBalance(number);
        out.writeObject(getBalance);
        out.flush();
        Object obj = null;
        try {
            obj = in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println( obj.getClass().getName());
        if (obj instanceof GetBalance){
            return ((GetBalance)obj).getBalance();
        }else if (obj instanceof Exceptions){
            for (Exception e : ((Exceptions) obj).getErrors()){
                e.printStackTrace();

            }
        }
        return 0;

    }
}
