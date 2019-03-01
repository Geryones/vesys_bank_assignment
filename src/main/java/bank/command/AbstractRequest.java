package bank.command;

import bank.Request;



public abstract class AbstractRequest implements Request {
    private String owner;
    private String number;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
