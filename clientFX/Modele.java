package main.java.clientFX;

import main.java.server.models.Course;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class Modele {
    public ObjectOutputStream oos ;
    private ObjectInputStream ois;

    public Modele(String address , int port) throws IOException {
        Socket client = new Socket(address, port);
        this.oos = new ObjectOutputStream(client.getOutputStream());
        this.ois = new ObjectInputStream(client.getInputStream());
    }



    public ArrayList<Course> chargeCourses(String s) throws IOException, ClassNotFoundException {
        //ArrayList<String> coursesString = new ArrayList<>();
        oos.writeObject("CHARGER " + s);
        oos.flush();
        oos.close();
        ArrayList<Course> courses = (ArrayList<Course>) ois.readObject();
        //for (int i = 0; i < courses.size(); i++) {
            // Les index paires contiendront les codes , impaires les noms
            //coursesString.add(courses.get(i).getCode().toString());
            //coursesString.add(courses.get(i).getName().toString());
        //}
        return courses;

    }


    }


