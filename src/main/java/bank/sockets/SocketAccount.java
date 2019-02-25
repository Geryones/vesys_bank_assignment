package bank.sockets;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;

/**
 * Created by Geryones on 25/02/2019.
 */
public class SocketAccount implements Account {
    private String number;
    private String owner;

    @Override public String getNumber() throws IOException {
        return null;
    }

    @Override public String getOwner() throws IOException {
        return null;
    }

    @Override public boolean isActive() throws IOException {
        return false;
    }

    @Override public void deposit(double amount) throws IOException, IllegalArgumentException, InactiveException {

    }

    @Override public void withdraw(double amount)
            throws IOException, IllegalArgumentException, OverdrawException, InactiveException {

    }

    @Override public double getBalance() throws IOException {
        return 0;
    }
}
