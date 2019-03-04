package bank.command;

import bank.Bank;
import bank.Command;
import bank.InactiveException;

import java.io.IOException;

public class Deposit extends AbstractCommand implements Command {
    private double amount;

    public Deposit(String number, double amount) {
        setNumber(number);
        this.amount = amount;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override Object execute(Bank bank) throws Exception {
        bank.getAccount(getNumber()).deposit(amount);
        return this;
    }

    @Override public void throwException() throws IOException,
            IllegalArgumentException, InactiveException  {
        if (getException() instanceof IOException){
            throw (IOException)getException();
        }
        if (getException() instanceof InactiveException){
            throw (InactiveException)getException();
        }
        if (getException() instanceof IllegalArgumentException){
            throw (IllegalArgumentException) getException();
        }
    }
}
