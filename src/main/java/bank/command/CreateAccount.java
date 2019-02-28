package bank.command;

import bank.Request;



public class CreateAccount implements Request {

    private String owner;
    private String accountNumber;

    public String getOwner() {
        return owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber){
        this.accountNumber = accountNumber;
    }

    public CreateAccount(String owner) {
        this.owner = owner;
    }



}
