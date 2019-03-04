package bank.command;

import bank.Bank;
import bank.Command;

public abstract class AbstractCommand implements Command {
    private Object result;
    private Exception exception = null;
    private String owner;
    private String number;

    abstract Object execute(Bank bank) throws Exception;

    public Object handle(Bank bank){
        try {
            Object result = execute(bank);
            this.result = result;
        }catch(Exception e){
            this.exception = e;
        }
        return this;
    }

    public void throwException() throws Exception {
        throw exception;
    }

    public boolean hasException(){
        return exception!=null;
    }

    public Object getResult() {
        return result;
    }

    public Exception getException() {
        return exception;
    }

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

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
