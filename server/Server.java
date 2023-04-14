package main.java.server;

import main.java.server.models.Course;
import main.java.server.models.RegistrationForm;

import javafx.util.Pair;

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
     * Constructeur de la classe Server
     * @param port: Port sur lequel le serveur écoute
     * @throws IOException: Lancée si une erreur arrive avec les entrées/sorties
     *
     */

    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     *
     * La méthode addEventHandler ajoute un EventHandler à la liste handlers
     * @param h: Instance du type EventHandler qui serait ajoutée
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
     * La méthode run serait appelée quand on veut exécuter le programme. Elle attend la connexion d'un client,
     * imprime un message, crée un ObjectInputStream et ObjectOutputStream et appelle les deux méthodes
     * listen et disconnect.
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
     * La méthode listen vérifie si la ligne de commande n'est pas null. Si c'est le cas, elle appelle la méthode
     * processCommandLine, découpe le Pair retourné aux variables cmd(Key) et arg(Value),
     * et passe cmd et arg à la méthode privée alertHandlers.
     * @throws IOException: lancée si une erreur arrive avec les entrées/sorties
     * @throws ClassNotFoundException: lancée si on essaye d'accéder une classe qui n'existe pas
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
     * @param line: ligne de texte à découper
     * @return Pair des deux variables cmd et args
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
     * La méthode disconnect est appelée si listen a été épuisée et ferme les Streams et le client.
     * @throws IOException: Lancée si une erreur arrive avec les entrées/sorties
     *
     */

    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     *
     * La méthode handleEvents va voir si cmd passée est soit REGISTER_COMMAND, soit LOAD_COMMAND. Si c'est le
     * cas, elle va appeler la méthode correspondante (handleLoadCourses et handleRegistration respectivement)
     * en passant arg si nécessaire (dans le cas de handleLoadCourses).
     * @param cmd: Commande du event spécifié
     * @param arg: Argument de la commande de l'event spécifié
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
     Lire un fichier texte contenant des informations sur les cours et les transformer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     La méthode gère les exceptions si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.
     @param arg la session pour laquelle on veut récupérer la liste des cours

     */

    public void handleLoadCourses(String arg)  {
        try{
            ArrayList<Course> courseList = new ArrayList<>();
            Scanner scan = new Scanner(new File("src/main/java/server/data/cours.txt"));
            do {
                String s = scan.nextLine();
                String[] fields = s.split("\t");
                Course c = new Course(fields[1], fields[0], fields[2]);
                if (arg.equals(fields[2])) {
                    courseList.add(c);
                }
            } while (scan.hasNext());
            objectOutputStream.writeObject(courseList);
            scan.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e){
            System.out.println("Erreur lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.");
        }
    }

    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     La méthode gère les exceptions si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        try {
            final String registrationFilePath = "src/main/java/server/data/inscription.txt";

            RegistrationForm registrationForm = (RegistrationForm) objectInputStream.readObject();
            Course course = registrationForm.getCourse();

            FileReader fr = new FileReader(registrationFilePath);
            BufferedReader reader = new BufferedReader(fr);

            String s;
            StringBuilder previousRegistrations = new StringBuilder();
            while ((s = reader.readLine()) != null) {
                previousRegistrations.append(s).append("\n");
            }
            reader.close();

            FileWriter fw = new FileWriter(registrationFilePath);
            BufferedWriter writer = new BufferedWriter(fw);

            writer.append(previousRegistrations + course.getSession() + "\t" + course.getCode() + "\t" +
                          registrationForm.getMatricule() + "\t" +
                          registrationForm.getPrenom() + "\t" +
                          registrationForm.getEmail());
            writer.close();
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            System.out.println("Erreur à l'écriture");
        }
    }
}

