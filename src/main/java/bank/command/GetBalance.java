package bank.command;

import bank.Request;

public class GetBalance extends AbstractRequest implements Request {

    private double balance;

    public GetBalance(String number) {
       setNumber(number);
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
