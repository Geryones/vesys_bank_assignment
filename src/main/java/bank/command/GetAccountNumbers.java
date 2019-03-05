package bank.command;

import bank.Bank;
import bank.Command;

import java.util.Set;

public class GetAccountNumbers extends AbstractCommand {

    private Set<String> accountNumbers;

    public GetAccountNumbers() {

    }
    public Set<String> getAccountNumbers() {
        return accountNumbers;
    }


    @Override Object execute(Bank bank) throws Exception {
        accountNumbers = bank.getAccountNumbers();
        return this;
    }
}
