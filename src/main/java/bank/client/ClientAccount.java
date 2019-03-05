package bank.client;

import bank.Account;
import bank.Command;
import bank.InactiveException;
import bank.OverdrawException;
import bank.command.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Geryones on 25/02/2019.
 */
public class ClientAccount implements Account {
    private final String number;
    private final String owner;


    public ClientAccount(String number, String owner) {
        this.number = number;
        this.owner = owner;

    }

    @Override public String getNumber(){
        return number;
    }

    @Override public String getOwner(){
        return owner;
    }

    @Override public boolean isActive() throws IOException {
        Command isActive = new IsActive(number);
        IsActive response = (IsActive) Driver.request(isActive);
        return  response.isActive();
    }

    @Override public void deposit(double amount) throws IOException,
            IllegalArgumentException, InactiveException {
        Command deposit = new Deposit(number, amount);
        Deposit response = (Deposit) Driver.request(deposit);
        if (response.hasException()){
            response.throwException();
        }
    }

    @Override public void withdraw(double amount)
            throws IOException, IllegalArgumentException,
            OverdrawException, InactiveException {
        Command withdraw = new Withdraw(number, amount);
        Withdraw response = (Withdraw) Driver.request(withdraw);
        if (response.hasException()){
            response.throwException();
        }

    }

    @Override public double getBalance() throws IOException {
        Command getBalance = new GetBalance(number);
        GetBalance response = (GetBalance) Driver.request(getBalance);
        return response.getBalance();
    }

}
