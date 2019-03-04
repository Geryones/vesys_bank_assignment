package bank.command;

import bank.Bank;
import bank.Command;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;

public class Withdraw extends AbstractCommand implements Command {

    private double amount;

    public Withdraw(String number, double amount) {
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
        bank.getAccount(getNumber()).withdraw(amount);
        return this;
    }

    @Override public void throwException() throws IOException,
            IllegalArgumentException,
            OverdrawException, InactiveException {
        if (getException() instanceof IOException) {
            throw (IOException) getException();
        }
        if (getException() instanceof InactiveException) {
            throw (InactiveException) getException();
        }
        if (getException() instanceof OverdrawException) {
            throw (OverdrawException) getException();
        }
        if (getException() instanceof IllegalArgumentException) {
            throw (IllegalArgumentException) getException();
        }

    }
}
