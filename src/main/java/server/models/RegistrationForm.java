package main.java.server.models;

import java.io.Serializable;

public class RegistrationForm implements Serializable {
    private final String prenom;
    private final String nom;
    private final String email;
    private final String matricule;
    private final Course course;

    public RegistrationForm(String prenom, String nom, String email, String matricule, Course course) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.matricule = matricule;
        this.course = course;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getMatricule() {
        return matricule;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "InscriptionForm{" + "prenom='" + prenom + '\'' + ", nom='" + nom + '\'' + ", email='" + email + '\'' + ", matricule='" + matricule + '\'' + ", course='" + course + '\'' + '}';
    }
}
