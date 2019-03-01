package bank.command;

import bank.Request;

public class IsActive extends AbstractRequest implements Request {
    private boolean isActive = false;

    public IsActive(String number) {
        setNumber(number);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
