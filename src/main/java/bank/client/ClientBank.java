package bank.client;

import bank.*;
import bank.command.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Geryones on 25/02/2019.
 */
public class ClientBank implements Bank{

    private final Map<String, ClientAccount> accounts = new HashMap<>();

    @Override public String createAccount(String owner) throws IOException {
        Command createAccount = new CreateAccount(owner);
        CreateAccount response = (CreateAccount) Driver.request(createAccount);
        return response.getNumber();
    }

    @Override public boolean closeAccount(String number) throws IOException {
       Command closeAccount = new CloseAccount(number);
       CloseAccount response = (CloseAccount) Driver.request(closeAccount);
       return response.isClosed();
    }

    @Override public Set<String> getAccountNumbers() throws IOException {
        Command getAccounts = new GetAccountNumbers();
        GetAccountNumbers response = (GetAccountNumbers) Driver.request(getAccounts);
        return response.getAccountNumbers();
    }

    @Override public Account getAccount(String number) throws IOException {
        if (!accounts.containsKey(number)) {
            Command getAccount = new GetAccount(number);
            GetAccount response = (GetAccount) Driver.request(getAccount);
            if (!response.doesExist()){
                return null;
            }
            ClientAccount temp = new ClientAccount(number,response.getOwner());
            accounts.put(number,temp);
        }

        return accounts.get(number);
    }

    @Override
    public void transfer(Account from, Account to, double amount)
            throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
       Command transfer = new Transfer(from, to, amount);
       Transfer response = (Transfer) Driver.request(transfer);

       if (response.hasException()){
           response.throwException();
       }
    }








}
