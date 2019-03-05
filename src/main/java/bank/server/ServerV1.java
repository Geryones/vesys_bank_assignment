package bank.server;

import bank.Command;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerV1 {
    private static ServerBank bank;
    public static void main(String[] args) {
        int port = 1234;
        bank = new ServerBank();

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Startet my Server V1 on port " + port);
            ExecutorService pool = Executors.newCachedThreadPool();
            while (true) {
                Socket s = server.accept();
                pool.execute(new Handler(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //daraus wird der bank server gebaut
    private static class Handler implements Runnable {
        private final Socket s;

        private final ObjectOutputStream out;
        private final ObjectInputStream in;


        Handler(Socket socket) throws IOException {
            this.s = socket;
            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());
        }

        public void run() {
            try {

                while (in != null) {

                    Command command = (Command) in.readObject();
                    out.writeObject(command.handle(bank));

                }

                System.out.println("done serving " + s);
                s.close();
                in.close();
                out.close();
            } catch (IOException e) {
                System.err.println(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

