package bank.client;

import bank.Bank;
import bank.BankDriver;

import java.io.IOException;
import java.net.Socket;


public class Driver implements BankDriver {
    private ClientBank bank = null;
    private int port;
    private String server;
    private Socket s;




    @Override
    public void connect(String[] args) throws IOException {
        port = Integer.parseInt(args[1]);
        server = args[0];
        s = new Socket(server, port);
        bank = new ClientBank(s);
        System.out.println("connected");
    }

    @Override
    public void disconnect() throws IOException {
        bank.disconnect();
        s.close();
        System.out.println("disconnected");
    }

    @Override
    public Bank getBank() {
        return bank;
    }
}
