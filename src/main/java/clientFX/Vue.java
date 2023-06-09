package main.java.clientFX;
import main.java.server.models.Course;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * La classe Vue qui est responsable de l'interface graphique
 */
public class Vue {
    private final Button charger = new Button("charger");
    private final Button envoyer = new Button("envoyer");
    private final ChoiceBox session = new ChoiceBox(FXCollections.observableArrayList("Automne","Hiver","Ete"));
    private final TableView<Course> table = new TableView<>();
    private final TableColumn codeColumn = new TableColumn("Code");
    private final TableColumn coursColumn = new TableColumn("Cours");
    private final HBox root = new HBox();
    private final TextField prenomField = new TextField();
    private final TextField nomField = new TextField();
    private final TextField emailField = new TextField();
    private final TextField matriculeField = new TextField();
    Alert alert = new Alert(Alert.AlertType.NONE);

    /**
     * getter du prénom
     *
     * @return le prénom
     */
    public TextField getPrenomField() {
        return prenomField;
    }

    /**
     * getter du nom
     *
     * @return le nom
     */

    public TextField getNomField() {
        return nomField;
    }

    /**
     * getter d'email
     *
     * @return l'email
     */

    public TextField getEmailField() {
        return emailField;
    }

    /**
     * getter de la matricule
     *
     * @return la matricule
     */

    public TextField getMatriculeField() {
        return matriculeField;
    }

    /**
     * getter de la table
     *
     * @return la table
     */

    public TableView<Course> getTable() {
        return table;
    }

    /**
     * méthode qui implémente les éléments de l'interface graphique
     */
    public Vue() {
        session.setValue("Automne");

        root.setStyle("-fx-background-color: #e5e1af");
        VBox left = new VBox();
        root.getChildren().add(left);

        Text ldc = new Text("Liste de cours");
        left.getChildren().add(ldc);
        left.setAlignment(Pos.CENTER);
        left.getChildren().add(table);
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        table.getColumns().addAll(codeColumn, coursColumn);

        Separator sep1 = new Separator();
        left.getChildren().add(sep1);

        HBox hBox = new HBox(10,session,charger);
        left.getChildren().add(hBox);
        hBox.setPadding(new Insets(10));
        hBox.setAlignment(Pos.CENTER);

        Separator sep2 = new Separator();
        root.getChildren().add(sep2);
        sep2.setOrientation(Orientation.VERTICAL);

        VBox right = new VBox();
        root.getChildren().add(right);

        Text fi = new Text("Formulaire d'inscription");
        right.getChildren().add(fi);
        right.setAlignment(Pos.CENTER);

        HBox prenomBox = new HBox();
        right.getChildren().add(prenomBox);
        prenomBox.setPadding(new Insets(10));
        prenomBox.setAlignment(Pos.CENTER);

        Text prenom = new Text("Prénom");
        prenomBox.getChildren().add(prenom);

        prenomBox.getChildren().add(prenomField);

        HBox nomBox = new HBox();
        right.getChildren().add(nomBox);
        nomBox.setPadding(new Insets(10));
        nomBox.setAlignment(Pos.CENTER);

        Text nom = new Text("Nom");
        nomBox.getChildren().add(nom);

        nomBox.getChildren().add(nomField);

        HBox emailBox = new HBox();
        right.getChildren().add(emailBox);
        emailBox.setPadding(new Insets(10));
        emailBox.setAlignment(Pos.CENTER);

        Text email = new Text("Email");
        emailBox.getChildren().add(email);

        emailBox.getChildren().add(emailField);

        HBox matriculeBox = new HBox();
        right.getChildren().add(matriculeBox);
        matriculeBox.setPadding(new Insets(10));
        matriculeBox.setAlignment(Pos.CENTER);

        Text matricule = new Text("Matricule");
        matriculeBox.getChildren().add(matricule);

        matriculeBox.getChildren().add(matriculeField);
        right.getChildren().add(envoyer);
        right.setAlignment(Pos.CENTER);

        new BackgroundFill(Color.YELLOW,CornerRadii.EMPTY, Insets.EMPTY);
    }
    /**
     * getter du boutton charger
     *
     * @return le boutton charger
     */

    public Button getCharger() {
        return charger;
    }

    /**
     * getter du boutton envoyer
     *
     * @return du boutton envoyer
     */
    public Button getEnvoyer() {
        return envoyer;
    }

    /**
     * getter de la racine
     *
     * @return la racine
     */
    public HBox getRoot() {
        return root;
    }
    /**
     * getter de la session choisie
     *
     * @return la session choisie
     */

    public ChoiceBox getSession() {
        return session;
    }

    /**
     * méthode qui met les cours dans la table
     *
     * @param courses liste des cours a mettre dans la table
     */
    public void loadCourses(ArrayList courses) {
        ObservableList<Course> data = FXCollections.observableArrayList(courses);
        for (int i=0 ; i<courses.size() ; i++){
            codeColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("code"));
            coursColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("name"));
        }
        table.setItems(data);
        table.setStyle("-fx-border-color: teal;");
    }
    /**
     * méthode qui change les bordures de la table dans le cas ou c'est faux
     */
    public void tableError() {
        table.setStyle("-fx-border-color: red;");
    }

    /**
     * méthode qui change les bordures d'un TextField dans le cas ou c'est faux
     *
     * @param textFields la liste des TextField pour lesquels on veut changer les bordures
     */
    public void textFieldsError(ArrayList<TextField> textFields) {
        for (int i = 0; i < textFields.size(); i++) {
            textFields.get(i).setStyle("-fx-border-color: red;");
        }
    }

    /**
     * méthode qui change les bordures de la table dans le cas ou c'est correct
     */

    public void correctTable() {
        table.setStyle("-fx-border-color: darkgrey;");
    }

    /**
     * méthode qui change les bordures d'un TextField dans le cas ou c'est correct
     *
     * @param textField le TextField pour lequel on veut changer les bordures
     */
    public void correctTextField(TextField textField) {
        textField.setStyle("-fx-border-color: darkgrey;");
    }

    /**
     * méthode qui sort un alert dans le cas d'une erreur
     *
     * @param message le message a alerté
     */
    public void errorMessage(String message) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
    /**
     * méthode qui sort un alert dans le cas ou c'est correct
     *
     * @param message le message a alerté
     */
    public void confirmationMessage(String message) {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.show();
    }
}





