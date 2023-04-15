package main.java.clientFX;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Controleur {
    private final Modele modele;
    private final Vue vue;

    public Controleur(Modele m, Vue v) {
        this.modele = m;
        this.vue = v;

        this.vue.getCharger().setOnAction((action) ->{
            try {
                this.charger();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        
        this.vue.getEnvoyer().setOnAction((action) ->{
            this.envoyer();
        });
    }

    private void envoyer() {
    }

    private void charger() throws FileNotFoundException {
        ArrayList courses ;
        try {
            courses = this.modele.chargeCourses(String.valueOf(this.vue.getSession().getSelectionModel().getSelectedItem()));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.vue.loadCourses(courses);
    }
}
