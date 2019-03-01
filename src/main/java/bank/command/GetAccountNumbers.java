package bank.command;

import bank.Request;

import java.util.Set;

public class GetAccountNumbers extends AbstractRequest implements Request {

    private Set<String> accountNumbers;

    public GetAccountNumbers() {

    }
    public Set<String> getAccountNumbers() {
        return accountNumbers;
    }

    public void setAccountNumbers(Set<String> accountNumbers) {
        this.accountNumbers = accountNumbers;
    }

}
