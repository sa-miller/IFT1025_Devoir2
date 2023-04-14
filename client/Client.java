package main.java.client;

import main.java.server.models.Course;
import main.java.server.models.RegistrationForm;

import java.io.*;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private final ObjectOutputStream oos ;
    private final ObjectInputStream ois;
    private String arg;

    private final Scanner scanner = new Scanner(System.in);

    private final String invalidChoiceMessage = "Veuillez entrer un entier positif valide.";
    private Course course;

    public Client(String address , int port) throws IOException {
        Socket client = new Socket(address, port);
        this.oos = new ObjectOutputStream(client.getOutputStream());
        this.ois = new ObjectInputStream(client.getInputStream());
    }

    public ArrayList<Object> run(int nextAction, ArrayList<Course> courses) throws IOException, ClassNotFoundException {
        switch (nextAction) {
            case 1 -> {
                System.out.println("*** Bienvenue au portail d'inscription de cours de l'UdeM ***");
                System.out.println("""
                        Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:
                        1.Automne
                        2.Hiver
                        3.Ete""");
                System.out.print("> Choix: ");
                int temp = scanner.nextInt();
                argValue(temp);
                if (!arg.equals("")) {
                    System.out.println("Les cours offerts pendant la session d'" + arg.toLowerCase() + " sont:");
                    oos.writeObject("CHARGER " + arg);
                    oos.flush();

                    courses = (ArrayList<Course>) ois.readObject();
                    printCourses(courses);
                }
            }
            case 2 -> {
                String[] answers = new String[5];
                String[] messages = {"Veuillez saisir votre prénom: ",
                        "Veuillez saisir votre nom: ",
                        "Veuillez saisir votre email: ",
                        "Veuillez saisir votre matricule: ",
                        "Veuillez saisir le code du cours: "};
                for (int i = 0; i < messages.length; i++) {
                    String message = messages[i];
                    System.out.print(message);
                    answers[i] = scanner.nextLine();
                }

                boolean courseFound = false;

                for (Course course : courses) {
                    if (course.getCode().equals(answers[4])) {
                        this.course = course;
                        courseFound = true;
                        break;
                    }
                }

                if (courseFound == true) {
                    RegistrationForm registrationForm = new RegistrationForm(answers[0], answers[1], answers[2], answers[3], this.course);
                    oos.writeObject("INSCRIRE");
                    oos.writeObject(registrationForm);
                    oos.flush();

                    System.out.println("\nFélicitations! Inscription réussie de " + answers[0] + " au cours " + answers[4] + ".");
                } else {
                    System.out.println("Aucun cours correspondant au code donné n'a été trouvé.");
                }
            }
            case 3 -> {
                oos.close();
            }
            default -> System.out.println(invalidChoiceMessage);
        }

        System.out.println("""
                Veuillez choisir la prochaine action:
                1. Consulter les cours offerts pour une autre session
                2. Inscription à un cours
                3. Déconnexion""");
        System.out.print("> Choix: ");

        nextAction = scanner.nextInt();

        oos.close();

        ArrayList<Object> runArgs = new ArrayList<>();

        runArgs.add(nextAction);
        runArgs.add(courses);

        return runArgs;
    }

    public void argValue(int temp) {
        switch (temp) {
            case 1 -> arg = "Automne";
            case 2 -> arg = "Hiver";
            case 3 -> arg = "Ete";
            default -> {arg = ""; System.out.println(invalidChoiceMessage);}
        }
    }

    public void printCourses(ArrayList<Course> courses) {
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            System.out.println((i + 1) + ". " + course.getName() + "\t" + course.getCode());
        }
    }

}

