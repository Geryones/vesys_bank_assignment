package bank.command;

import bank.Bank;
import bank.Command;

public class GetBalance extends AbstractCommand implements Command {

    private double balance;

    public GetBalance(String number) {
       setNumber(number);
    }


    public double getBalance() {
        return balance;
    }



    @Override Object execute(Bank bank) throws Exception {
        balance = bank.getAccount(getNumber()).getBalance();
        return this;
    }
}
