package bank.command;

import bank.Request;



public class CreateAccount extends AbstractRequest implements Request {

    public CreateAccount(String owner) {
       setOwner(owner);
    }



}
