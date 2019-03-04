package bank.command;

import bank.Bank;
import bank.Command;

public class IsActive extends AbstractCommand implements Command {
    private boolean isActive;

    public IsActive(String number) {
        setNumber(number);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override Object execute(Bank bank) throws Exception {
        setActive(bank.getAccount(getNumber()).isActive());
        return this;
    }
}
