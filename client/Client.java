package main.java.client;

import java.io.*;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private final ObjectOutputStream oos ;
    private final ObjectInputStream ois;
    private String arg;

    private final Scanner scanner = new Scanner(System.in);
    public Client(String address , int port) throws IOException {
        Socket client = new Socket(address, port);
        this.oos = new ObjectOutputStream(client.getOutputStream());
        this.ois = new ObjectInputStream(client.getInputStream());
    }

    public boolean run() throws IOException, ClassNotFoundException {
        System.out.println("*** Bienvenue au portail d'inscription de cours de l'UdeM ***");
        System.out.println("""
                Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:");
                1.Automne
                2.Hiver
                3.Ete""");
        System.out.print("> Choix: ");
        int temp = scanner.nextInt();
        argValue(temp);
        System.out.println("Les cours offerts pendant la session d'" + arg.toLowerCase() +" sont:");
        oos.writeObject("CHARGER " + arg);
        oos.flush();
        printCourses();

        oos.close();

        return true;
    }

    public void argValue(int temp) {
        switch (temp) {
            case 1 -> arg = "Automne";
            case 2 -> arg = "Hiver";
            case 3 -> arg = "Ete";
        }
    }

    public void printCourses() throws IOException, ClassNotFoundException {
        ArrayList<Object> courses = (ArrayList<Object>) ois.readObject();
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i).toString());
        }
    }

}

