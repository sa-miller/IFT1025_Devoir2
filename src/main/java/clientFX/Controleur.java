package main.java.clientFX;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import main.java.server.models.Course;

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
        try {
            TableView<Course> table = vue.getTable();
            TablePosition pos = vue.getTable().getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            TableColumn col = table.getColumns().get(0);

            this.modele.gererInscription(this.vue.getPrenomField().getText(),
                                         this.vue.getNomField().getText(),
                                         this.vue.getEmailField().getText(),
                                         this.vue.getMatriculeField().getText(),
                                         (String) col.getCellObservableValue(table.getItems().get(row)).getValue());

        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void charger() throws FileNotFoundException {
        ArrayList courses;
        try {
            courses = this.modele.chargeCourses(String.valueOf(this.vue.getSession().getSelectionModel().getSelectedItem()));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.vue.loadCourses(courses);
    }
}
