package server;

import javafx.util.Pair;
import server.models.Course;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * La classe Serveur définit des commandes que le client peut envoyer pour utiliser le serveur.
 */

public class Server {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
     *
     * Le constructeur de la classe Server
     * @param port Le port sur lequel le serveur écoute
     * @throws IOException lancé si une erreur arrive avec les entrées/sorties
     *
     */

    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     *
     * Cette méthode ajoute un EventHandler à la liste handlers
     * @param h instance du type EventHandler qui serait ajoutée
     *
     */

    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }


    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     *
     * La méthode run serait appelée quand si on veut executer le programme , elle va attendre la connexion d'un client
     * puis va imprimer un message et créer un ObjectInputStream et ObjectOutputStream puis appelle les deux méthodes
     * listen et puis disconnect
     *
     */

    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * La méthode listen chéque si la ligne de commande n'est pas nul et si c'est le cas et appelle la méthode
     * processCommandLine, découpe le Pair retourné aux variables cmd(Key) et arg(Value)
     * puis passe cmd et arg à la méthode privée alertHandlers
     * @throws IOException lancé si une erreur arrive avec les entrées/sorties
     * @throws ClassNotFoundException lancé si on essaye d'accéder une classe qui n'existe pas
     *
     */

    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     *
     * La méthode Pair découpe la commande donnée et donne à la variable cmd le premier mot(= séries de caractères
     * séparés par des espaces) et le reste serait assigné à la variable args
     * @param line à découper
     * @return un Pair des deux variables cmd et args
     *
     */

    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     *
     * La méthode disconnect est appelée si listen a été épuisée , et ferme les Streams et le client.
     * @throws IOException lancé si une erreur arrive avec les entrées/sorties
     *
     */

    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     *
     * La méthode handleEvents va voir si cmd passée est soit REGISTER_COMMAND soit LOAD_COMMAND , si c'est le
     * cas , elle va appeler la méthode correspondante (handleLoadCourses et handleRegistration respectivement)
     * en passant arg si nécessaire(dans le cas de handleLoadCourses).
     * @param cmd La commande du event spécifié
     * @param arg L'argument de la commande de l'event spécifié
     *
     */

    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     Lire un fichier texte contenant des informations sur les cours et les transofmer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     La méthode gère les exceptions si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.
     @param arg la session pour laquelle on veut récupérer la liste des cours

     */

    public void handleLoadCourses(String arg)  {
        int i ;
        try{
            ArrayList<Course> courseList = new ArrayList<Course>();
            Scanner scan = new Scanner(new File("cours.txt"));
            while (scan.hasNext()){
                String s = scan.nextLine();
                String[] fields = s.split("\t");
                Course c = new Course(fields[0] , fields[1] , fields[2]);

                if (arg == c.getSession()) {
                    courseList.add(c);
                }

            FileOutputStream fos = new FileOutputStream("courses.dat"); //Demande une question sur ca
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (i=0 ; i<courseList.size() ; i++) {
                oos.writeObject(c);
            }

            }


        } catch (FileNotFoundException e ) {
            throw new RuntimeException(e);
        } catch (IOException e){
            System.out.println("erreur lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.");
        }
    }

    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     La méthode gére les exceptions si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        // TODO: implémenter cette méthode
    }
}

