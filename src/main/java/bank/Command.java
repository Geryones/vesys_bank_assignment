package bank;

import java.io.Serializable;

public interface Command extends Serializable{
    Object handle(Bank bank);
    boolean hasException();
    Exception getException();
    void throwException() throws Exception;


}
