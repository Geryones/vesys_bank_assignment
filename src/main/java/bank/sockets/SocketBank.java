package bank.sockets;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Geryones on 25/02/2019.
 */
public class SocketBank implements Bank {
    @Override public String createAccount(String owner) throws IOException {
        return null;
    }

    @Override public boolean closeAccount(String number) throws IOException {
        return false;
    }

    @Override public Set<String> getAccountNumbers() throws IOException {
        return null;
    }

    @Override public Account getAccount(String number) throws IOException {
        return null;
    }

    @Override public void transfer(Account a, Account b, double amount)
            throws IOException, IllegalArgumentException, OverdrawException, InactiveException {

    }
}
