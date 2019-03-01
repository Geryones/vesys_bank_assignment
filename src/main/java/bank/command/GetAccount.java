package bank.command;

import bank.Account;
import bank.Request;

import java.io.IOException;

public class GetAccount extends AbstractRequest implements Request {

    private double balance;
    private boolean active;

    public GetAccount(String number) {
        setNumber(number);

    }

    public GetAccount(Account serverAccount) throws IOException {
        this.balance = serverAccount.getBalance();
        this.active = serverAccount.isActive();
        setOwner(serverAccount.getOwner());
        setNumber(serverAccount.getNumber());

    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
