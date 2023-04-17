package main.java.client;

import main.java.server.models.Course;

import java.util.ArrayList;

/**
 * La classe qui démarre le client
 */
public class ClientLauncher {

    /**
     * Port sur lequel le client se connecte
     */
    public final static int PORT = 1337;
    /**
     * Adresse IP de connexion
     */
    public final static String ADDRESS = "127.0.0.1";

    /**
     * La méthode main de la classe Client launcher
     *
     * @param args
     */

    public static void main(String[] args) {
        Client client;
        try {
            ArrayList<Object> runArgs = new ArrayList<>();
            runArgs.add(1);
            runArgs.add(new ArrayList<>());

            int nextAction = 1;

            while (nextAction != 3) {
                nextAction = (int) runArgs.get(0);

                client = new Client(ADDRESS,PORT);
                runArgs = client.run(nextAction, (ArrayList<Course>) runArgs.get(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


