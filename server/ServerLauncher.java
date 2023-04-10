/**
 * ServerLauncher définit les variables et méthodes nécessaires pour lancer le serveur.
 * Elle contient donc la méthode main.
 */

public class ServerLauncher {

    public final static int PORT = 1337;

    /**
     * La méthode main lance le serveur lorsqu'on roule le programme.
     * @param args: arguments du programme
     */

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(PORT);
            System.out.println("Server is running...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}