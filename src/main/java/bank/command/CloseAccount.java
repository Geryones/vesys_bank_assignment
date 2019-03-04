package bank.command;

import bank.Bank;
import bank.Command;

public class CloseAccount extends AbstractCommand implements Command {
    private boolean closed = true;

    public CloseAccount(String number){
        setNumber(number);
    }

    @Override Object execute(Bank bank) throws Exception {
        closed = bank.closeAccount(getNumber());
        return this;
    }

    public boolean isClosed() {
        return closed;
    }
}


