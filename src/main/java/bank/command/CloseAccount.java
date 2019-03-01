package bank.command;

import bank.Request;

public class CloseAccount extends AbstractRequest implements Request {

    private boolean removed;

    public CloseAccount(String number) {
        setNumber(number);
        removed = false;
    }


    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}


