package bank.server;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public  class ServerBank implements Bank {

    public ServerBank(){}


    private final Map<String, ServerAccount> accounts = new HashMap<>();

    @Override
    public Set<String> getAccountNumbers() {
        return accounts.values().stream().filter(
                a -> a.isActive()).map(
                        a -> a.getNumber()).collect(
                                Collectors.toSet());
    }

    @Override
    public String createAccount(String owner) {
        ServerAccount temp = new ServerAccount(owner,  "10-"+accounts.size()+"-0");
        accounts.put(temp.getNumber(), temp);
        return temp.getNumber();
    }

    @Override
    public boolean closeAccount(String number)throws IOException {
        if (accounts.containsKey(number)){
            if (accounts.get(number).getBalance() == 0.0){
                if (accounts.get(number).isActive()){
                    accounts.get(number).deActivate();
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public bank.Account getAccount(String number) {
        return accounts.get(number);
    }

    @Override
    public void transfer(bank.Account from, bank.Account to, double amount)
            throws IOException, InactiveException, OverdrawException {
        from.withdraw(amount);
        try {
            to.deposit(amount);
        }catch(InactiveException e){
            from.deposit(amount);
            System.out.println("unable to transfer money, TO-Acc is inactive");
            throw e;
        }
    }

}
