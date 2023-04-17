package main.java.clientFX;

import main.java.server.models.Course;
import main.java.server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * La classe modele qui contient la logique de notre ClientFX
 */
public class Modele {
    private final String address;
    private final int port;
    public ObjectOutputStream oos ;
    private ObjectInputStream ois;
    private Course course;
    private ArrayList<Course> courses;

    /**
     * Constructeur du modele
     *
     * @param address Adresse IP de connexion
     * @param port port sur lequel le client se connecte
     */
    public Modele(String address , int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * méthode qui connecte le clientFX avec le serveur
     *
     * @throws IOException Lancée si une erreur arrive avec les entrées/sorties
     */
    public void connect() throws IOException {
        Socket client = new Socket(address, port);
        this.oos = new ObjectOutputStream(client.getOutputStream());
        this.ois = new ObjectInputStream(client.getInputStream());
    }

    /**
     *La méthode qui charge les cours disponibles
     *
     * @param s la session
     * @return la liste d'objet Courses disponibles
     * @throws IOException lancée si une erreur arrive avec les entrées/sorties
     * @throws ClassNotFoundException lancée si on trouve pas la classe désirée
     */
    public ArrayList<Course> chargeCourses(String s) throws IOException, ClassNotFoundException {
        connect();
        oos.writeObject("CHARGER " + s);
        oos.flush();

        courses = (ArrayList<Course>) ois.readObject();

        oos.close();
        return courses;
    }

    /**
     * La méthode qui gere l'inscription d'un étudiant
     *
     * @param prenom prénom de l'étuidant
     * @param nom nom de l'étudiant
     * @param email email de l'étudiant
     * @param matricule matricule de l'étudiant
     * @param code code du cours
     * @return booléen , true si le cours est présent et false sinon
     * @throws IOException lancée si une erreur arrive avec les entrées/sorties
     */
    public boolean gererInscription(String prenom, String nom, String email, String matricule, String code) throws IOException {
        connect();
        boolean courseFound = false;

        for (Course course : courses) {
            if (course.getCode().equals(code)) {
                this.course = course;
                courseFound = true;
                break;
            }
        }

        if (courseFound) {
            RegistrationForm registrationForm = new RegistrationForm(prenom, nom, email, matricule, this.course);
            oos.writeObject("INSCRIRE");
            oos.writeObject(registrationForm);
            oos.flush();

            oos.close();
        } else {
            return false;
        }

        return true;
    }
}


