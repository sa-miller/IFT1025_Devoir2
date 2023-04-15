package main.java.clientFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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




    public Vue() {
        VBox left = new VBox();
        root.getChildren().add(left);

        Text ldc = new Text("Liste de cours");
        left.getChildren().add(ldc);
        ldc.setTextAlignment(TextAlignment.CENTER);
        left.getChildren().add(table);
        table.setEditable(true);
        table.getColumns().addAll(codeColumn, coursColumn);

        Separator sep1 = new Separator();
        left.getChildren().add(sep1);

        HBox hBox = new HBox();
        left.getChildren().add(hBox);
        hBox.getChildren().add(session);
        hBox.getChildren().add(charger);

        VBox right = new VBox();
        root.getChildren().add(right);

        Separator sep2 = new Separator();
        root.getChildren().add(sep2);
        sep2.setOrientation(Orientation.VERTICAL);

        Text fi = new Text("Formulaire d'inscription");
        right.getChildren().add(fi);
        fi.setTextAlignment(TextAlignment.CENTER);

        HBox prenomBox = new HBox();
        right.getChildren().add(prenomBox);

        Text prenom = new Text("Prénom");
        prenomBox.getChildren().add(prenom);

        TextField prenomField = new TextField();
        prenomBox.getChildren().add(prenomField);

        HBox nomBox = new HBox();
        right.getChildren().add(nomBox);

        Text nom = new Text("Nom");
        nomBox.getChildren().add(nom);

        TextField nomField = new TextField();
        nomBox.getChildren().add(nomField);

        HBox emailBox = new HBox();
        right.getChildren().add(emailBox);

        Text email = new Text("Email");
        emailBox.getChildren().add(email);

        TextField emailField = new TextField();
        emailBox.getChildren().add(emailField);

        HBox matriculeBox = new HBox();
        right.getChildren().add(matriculeBox);

        Text matricule = new Text("Matricule");
        matriculeBox.getChildren().add(matricule);

        TextField matriculeField = new TextField();
        matriculeBox.getChildren().add(matriculeField);
        right.getChildren().add(envoyer);

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



