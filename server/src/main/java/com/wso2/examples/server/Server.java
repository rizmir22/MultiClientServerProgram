package com.wso2.examples.server;

import java.util.*;
import java.net.*;
import java.util.concurrent.*;
import java.io.*;

public class Server implements Runnable {
    private static final int port = 1978;
    private final ServerSocket serverSocket;
    private final ExecutorService pool;

    public Server(int port, int poolSize) throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(poolSize);
    }

    /**
     * The main server entry point..
     */
    public static void main(String[] args) throws Exception {
        Server server = new Server(Server.port, 10);

        server.run();
    }

    public void run() {
        try {
            for (; ; ) {
                pool.execute(new Handler(serverSocket.accept()));
            }
        } catch (IOException ex) {
            pool.shutdown();
        }
    }

    static class Handler implements Runnable, Comparable<Handler> {
        private volatile static Set<Handler> allClients = new ConcurrentSkipListSet<Handler>();
        private final Socket socket;
        private final PrintStream out;
        private final Scanner input;

        Handler(Socket socket) throws IOException {
            this.socket = socket;

            InputStream is = socket.getInputStream();
            input = new Scanner(is);

            out = new PrintStream(socket.getOutputStream());
            out.flush();

            allClients.add(this);
        }

        public void run() {

            out.println("Welcome You are connected to the Server....!");

            String lastMessage;

            do {
                lastMessage = input.nextLine();

                for (Handler h : Handler.allClients) {
                    if (!h.equals(this)) {
                        h.sendMessage();
                    }
                }
            } while (lastMessage.indexOf("quit") != 0);

            out.println("You are Disconnected");

            out.close();

            allClients.remove(this);
        }

        public synchronized void sendMessage() {
            out.println("If You want to dicsconnect please type as quit");
        }

        public int compareTo(Handler other) {
            return 1;
        }
    }
}
