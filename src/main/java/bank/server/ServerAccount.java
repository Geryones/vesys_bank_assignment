package bank.server;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.Serializable;

public class ServerAccount implements Account{
    private final String number;
    private final String owner;
    private double balance;
    private boolean active;


    public ServerAccount(String owner, String number) {
        this.owner = owner;
        this.number = number;
        this.balance = 0;
        this.active = true;
    }


    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void deActivate(){
        this.active = false;
    }

    @Override
    public void deposit(double amount) throws InactiveException, IllegalArgumentException {
        if (!isActive()){
            throw new InactiveException("account is not active");
        }
        if (amount < 0){
            throw new IllegalArgumentException("no negative deposit");
        }
        balance += Math.abs(amount);
    }

    @Override
    public void withdraw(double amount) throws InactiveException, OverdrawException {
        if (!isActive()){
            throw new InactiveException("account not active");
        }
        if (balance < Math.abs(amount)){
            throw new OverdrawException("this is no Kontokorrent, you lack money");
        }
        balance -= Math.abs(amount);
    }

}
