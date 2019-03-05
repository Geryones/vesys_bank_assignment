package bank.command;

import bank.Bank;
import bank.Command;


public class CreateAccount extends AbstractCommand {

    public CreateAccount(String owner) {
       setOwner(owner);
    }

    @Override Object execute(Bank bank) throws Exception {
        this.setNumber(bank.createAccount(getOwner()));
        return this;
    }
}
