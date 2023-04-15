package main.java.clientFX;

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
import main.java.server.models.Course;



import java.util.ArrayList;

public class Vue {
    private final Button charger = new Button("charger");
    private final Button envoyer = new Button("envoyer");
    private final ChoiceBox session = new ChoiceBox(FXCollections.observableArrayList("Automne","Hiver","Ete"));
    private final TableView<Course> table = new TableView<Course>();
    private final TableColumn codeColumn = new TableColumn("Code");
    private final TableColumn coursColumn = new TableColumn("Cours");
    private final HBox root = new HBox();
    private final TextField prenomField = new TextField();
    private final TextField nomField = new TextField();
    private final TextField emailField = new TextField();
    private final TextField matriculeField = new TextField();

    public TextField getPrenomField() {
        return prenomField;
    }

    public TextField getNomField() {
        return nomField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getMatriculeField() {
        return matriculeField;
    }

    public TableView<Course> getTable() {
        return table;
    }

    public Vue() {
        root.setStyle("-fx-background-color: #e5e1af");
        VBox left = new VBox();
        left.setSpacing(10.0);
        root.getChildren().add(left);

        Text ldc = new Text("Liste de cours");
        left.getChildren().add(ldc);
        left.setAlignment(Pos.CENTER);
        left.getChildren().add(table);
        table.setEditable(true);
        table.getColumns().addAll(codeColumn, coursColumn);

        Separator sep1 = new Separator();
        left.getChildren().add(sep1);

        HBox hBox = new HBox();
        left.getChildren().add(hBox);
        hBox.getChildren().add(session);
        hBox.getChildren().add(charger);

        Separator sep2 = new Separator();
        root.getChildren().add(sep2);
        sep2.setOrientation(Orientation.VERTICAL);

        VBox right = new VBox();
        root.getChildren().add(right);
        right.setSpacing(10.0);

        Text fi = new Text("Formulaire d'inscription");
        right.getChildren().add(fi);
        right.setAlignment(Pos.CENTER);

        HBox prenomBox = new HBox();
        right.getChildren().add(prenomBox);

        Text prenom = new Text("Prénom");
        prenomBox.getChildren().add(prenom);

        prenomBox.getChildren().add(prenomField);

        HBox nomBox = new HBox();
        right.getChildren().add(nomBox);

        Text nom = new Text("Nom");
        nomBox.getChildren().add(nom);

        nomBox.getChildren().add(nomField);

        HBox emailBox = new HBox();
        right.getChildren().add(emailBox);

        Text email = new Text("Email");
        emailBox.getChildren().add(email);

        emailBox.getChildren().add(emailField);

        HBox matriculeBox = new HBox();
        right.getChildren().add(matriculeBox);

        Text matricule = new Text("Matricule");
        matriculeBox.getChildren().add(matricule);

        matriculeBox.getChildren().add(matriculeField);
        right.getChildren().add(envoyer);
        right.setAlignment(Pos.CENTER);

        new BackgroundFill(Color.YELLOW,CornerRadii.EMPTY, Insets.EMPTY);
    }
    public Button getCharger() {
        return charger;
    }

    public Button getEnvoyer() {
        return envoyer;
    }

    public HBox getRoot() {
        return root;
    }

    public ChoiceBox getSession() {
        return session;
    }

    public void loadCourses(ArrayList courses) {
        ObservableList<Course> data = FXCollections.observableArrayList(courses);
        for (int i=0 ; i<courses.size() ; i++){
            codeColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("code"));
            coursColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("name"));
        }
        table.setItems(data);
    }
}





