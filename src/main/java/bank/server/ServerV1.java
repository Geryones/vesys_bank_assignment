package bank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerV1 {
    public static void main(String[] args) {
        int port = 1234;

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Startet my Server V1 on port " + port);
            ExecutorService pool = Executors.newCachedThreadPool();
            while (true) {
                Socket s = server.accept();
                pool.execute(new Task(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //daraus wird der bank server gebaut
    private static class Task implements Runnable {
        private final Socket s;

        Task(Socket socket) {
            this.s = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                 PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {

                String input = in.readLine();
                while (input != null && !"".equals(input)) {
                    out.println(input);
                    input = in.readLine();
                }
                System.out.println("done serving " + s);
                s.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}

