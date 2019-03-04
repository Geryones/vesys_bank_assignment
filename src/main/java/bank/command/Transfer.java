package bank.command;

import bank.*;

import java.io.IOException;

public class Transfer extends AbstractCommand implements Command {
    private String accountNumberFrom, accountNumberTo;
    private double amount;

    public Transfer(Account from, Account to, double amount) {
        try {
            accountNumberFrom = from.getNumber();
            accountNumberTo = to.getNumber();
        }catch (IOException e){
            setException(e);
        }
        this.amount = amount;
    }

    @Override Object execute(Bank bank)  {
        try {
            bank.transfer(bank.getAccount(accountNumberFrom),
                    bank.getAccount(accountNumberTo), amount);
        }catch (Exception e){
            setException(e);
        }
        return this;
    }

    @Override
    public void throwException() throws IOException,
            InactiveException, OverdrawException {
       if (getException() instanceof IOException){
           throw (IOException)getException();
       }
       if (getException() instanceof InactiveException){
           throw (InactiveException)getException();
       }
       if (getException() instanceof OverdrawException){
           throw (OverdrawException) getException();
       }

    }
}
