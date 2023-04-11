package client ;

import server.Server;

public class ClientLauncher {

    public final static int PORT = 1337;
    public final static String ADDRESS = "127.0.0.1";

    public static void main(String[] args) {
        Client client;
        try {
            client = new Client(ADDRESS,PORT);
            client.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


