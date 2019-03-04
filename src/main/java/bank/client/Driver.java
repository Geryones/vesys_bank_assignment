package bank.client;

import bank.Bank;
import bank.BankDriver;
import bank.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Driver implements BankDriver {
    private ClientBank bank = null;
    private int port;
    private String server;
    private Socket s;
    private static ObjectOutputStream out;
    private static ObjectInputStream in ;

    @Override
    public void connect(String[] args) throws IOException {
        port = Integer.parseInt(args[1]);
        server = args[0];
        s = new Socket(server, port);
        bank = new ClientBank();
        out = new ObjectOutputStream(s.getOutputStream());
        in = new ObjectInputStream(s.getInputStream());
        System.out.println("connected");
    }

    @Override
    public void disconnect() throws IOException {
        s.close();
        in.close();
        out.close();
        System.out.println("disconnected");
    }

    @Override
    public Bank getBank() {
        return bank;
    }

    public static Command request(Command command) throws IOException {
        Command response = null;
        try {
            out.writeObject(command);
            out.flush();
            response = (Command) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
