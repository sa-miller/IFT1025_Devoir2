package main.java.clientFX;

import main.java.server.models.Course;
import main.java.server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class Modele {
    private final String address;
    private final int port;
    public ObjectOutputStream oos ;
    private ObjectInputStream ois;
    private Course course;
    private ArrayList<Course> courses;

    public Modele(String address , int port) {
        this.address = address;
        this.port = port;
    }

    public void connect() throws IOException {
        Socket client = new Socket(address, port);
        this.oos = new ObjectOutputStream(client.getOutputStream());
        this.ois = new ObjectInputStream(client.getInputStream());
    }

    public ArrayList<Course> chargeCourses(String s) throws IOException, ClassNotFoundException {
        connect();
        oos.writeObject("CHARGER " + s);
        oos.flush();

        courses = (ArrayList<Course>) ois.readObject();

        oos.close();
        return courses;
    }

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


