package main.java.clientFX;

import java.io.FileNotFoundException;

public class Controleur {
    private Modele modele;
    private Vue vue;

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
        this.modele.chargeCourses(String.valueOf(this.vue.getSession()));
        this.vue.loadCourses();
    }
}
