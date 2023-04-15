package main.java.clientFX;

import main.java.server.models.Course;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class Modele {
    private String address;
    private int port;
    public ObjectOutputStream oos ;
    private ObjectInputStream ois;

    public Modele(String address , int port) throws IOException {
        this.address = address;
        this.port = port;
    }

    public ArrayList<Course> chargeCourses(String s) throws IOException, ClassNotFoundException {
        Socket client = new Socket(address, port);
        this.oos = new ObjectOutputStream(client.getOutputStream());
        this.ois = new ObjectInputStream(client.getInputStream());

        oos.writeObject("CHARGER " + s);
        oos.flush();
        ArrayList<Course> courses = (ArrayList<Course>) ois.readObject();
        return courses;

    }


    }


