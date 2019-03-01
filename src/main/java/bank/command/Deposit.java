package bank.command;

import bank.Request;

public class Deposit extends AbstractRequest implements Request {
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
}
