package bank.command;


import bank.Account;
import bank.Bank;
import bank.Command;

import java.io.IOException;

public class GetAccount extends AbstractCommand {
    private boolean exists = true;

    public GetAccount(String number) {
        setNumber(number);

    }

    private void copyAccount(Account account){
        try {
            setOwner(account.getOwner());
        }catch (IOException e){
            setException(e);
        }

    }

    @Override Object execute(Bank bank) throws Exception {
        if (bank.getAccount(getNumber()) != null){
            copyAccount(bank.getAccount(getNumber()));
        }else {
            exists = false;
        }
        return this;
    }
    public boolean doesExist(){
        return exists;
    }
}
