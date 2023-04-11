package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final Socket client ;
    private ObjectOutputStream oos ;
    private ObjectInputStream ois;
    private String arg;

    private Scanner scanner = new Scanner(System.in);
    public Client(String address , int port) throws IOException {
        this.client = new Socket(address, port);
        this.oos = new ObjectOutputStream(client.getOutputStream());
        this.ois = new ObjectInputStream(client.getInputStream());
    }

    public void run () throws IOException, ClassNotFoundException {
        System.out.println(" *** Bienvenue au portail d'inscription de cours de l'UdeM ***");
        System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:");
        System.out.println("1.Automne");
        System.out.println("2.Hiver");
        System.out.println("3.Ete");
        System.out.print("> Choix:");
        while (scanner.hasNext()) {
            int temp = scanner.nextInt();
            //String arg = Integer.toString(temp);
            argValue(temp);
            oos.writeObject("CHARGER " + arg);
            oos.flush();
            printCourses();
        }
        oos.close();
        scanner.close();

    }

    public String argValue(int temp) {
        switch(temp){
            case 1 :
                return arg = "Automne";
            case 2 :
                return arg = "Hiver";
            case 3 :
                return arg = "Ete";
        }
        return null;
    }

    public void printCourses() throws IOException, ClassNotFoundException {
        System.out.println(ois.readObject());

    }

}

