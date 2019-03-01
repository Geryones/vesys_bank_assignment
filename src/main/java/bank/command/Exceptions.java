package bank.command;

import bank.Request;

import java.util.ArrayList;
import java.util.List;

public class Exceptions extends AbstractRequest implements Request {

    private List<Exception> errors = new ArrayList<>();


    public List<Exception> getErrors() {
        return errors;
    }

    public void setError(Exception e) {
        errors.add(e);
    }

    public boolean noErrors(){
        return errors.isEmpty();
    }
}
