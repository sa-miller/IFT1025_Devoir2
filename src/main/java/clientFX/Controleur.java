package main.java.clientFX;
import main.java.server.models.Course;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * La classe Controleur qui connecte la Vue et le Modele
 */
public class Controleur {
    private final Modele modele;
    private final Vue vue;

    /**
     * Le consructeur du Controleur
     *
     * @param m le modele
     * @param v la vue
     */
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
        
        this.vue.getEnvoyer().setOnAction((action) -> this.envoyer());
    }

    private void envoyer() {
        final String baseErrorMessage = "Le formulaire est invalide.";
        StringBuilder errorMessage = new StringBuilder(baseErrorMessage);

        TextField[] textFields = {this.vue.getPrenomField(), this.vue.getNomField(), this.vue.getEmailField(), this.vue.getMatriculeField()};
        ArrayList<TextField> errorTextFields = new ArrayList<>();

        String[] regex = {"[a-zA-Z ,.'-]+", "[a-zA-Z ,.'-]+", "\\S+@\\S+.\\S+", "[0-9]{8}"};
        String[] champs = {"Prenom", "Nom", "Email", "Matricule"};

        boolean registrationIsCorrect = true;

        for (int i = 0; i < textFields.length; i++) {
            TextField textField = textFields[i];
            String text = textField.getText();
            if (!text.matches(regex[i])) {
                errorMessage.append("\nLe champ '").append(champs[i]).append("' est invalide!");
                errorTextFields.add(textField);
                registrationIsCorrect = false;
            } else {
                this.vue.correctTextField(textField);
            }
        }

        try {
            TableView<Course> table = vue.getTable();
            ObservableList<TablePosition> selectedCellsInfo = vue.getTable().getSelectionModel().getSelectedCells();
            if (selectedCellsInfo.size() != 0) {
                TablePosition pos = vue.getTable().getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                TableColumn col = table.getColumns().get(0);

                if (registrationIsCorrect) {
                    String prenom = textFields[0].getText();
                    String nom = textFields[1].getText();
                    String email = textFields[2].getText();
                    String matricule = textFields[3].getText();
                    String code = (String) col.getCellObservableValue(table.getItems().get(row)).getValue();

                    boolean registrationSuccess = this.modele.gererInscription(prenom, nom, email, matricule, code);
                    if (registrationSuccess) {
                        this.vue.confirmationMessage("\nFélicitations! Inscription réussie de " + prenom + " au cours " + code + ".");
                    } else{
                        this.vue.errorMessage("Aucun cours correspondant au code donné n'a été trouvé.");
                    }
                    for (TextField textField: textFields) {
                        this.vue.correctTextField(textField);
                    }
                }

                this.vue.correctTable();
            } else {
                this.vue.tableError();
                errorMessage.append("\nVous devez sélectionner un cours!");
            }

            String errorMessageString = errorMessage.toString();
            if (!errorMessageString.equals(baseErrorMessage)) {
                this.vue.errorMessage(errorMessageString);
                this.vue.textFieldsError(errorTextFields);
            }
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
