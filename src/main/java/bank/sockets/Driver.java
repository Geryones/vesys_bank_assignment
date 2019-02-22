package bank.sockets;

import bank.Bank;
import bank.BankDriver;

import java.io.IOException;


public class Driver implements BankDriver {




    @Override
    public void connect(String[] args) throws IOException {

    }

    @Override
    public void disconnect() throws IOException {

    }

    @Override
    public Bank getBank() {
        return null;
    }
}
