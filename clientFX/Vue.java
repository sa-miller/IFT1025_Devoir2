package main.java.clientFX;

import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Vue extends Parent {

    private Button charger = new Button("charger");
    private Button envoyer = new Button("envoyer");
    private ChoiceBox session = new ChoiceBox(FXCollections.observableArrayList("Automne","Hiver","Ete"));
    private Text ldc = new Text("Liste de cours");
    private Text fi = new Text("Formulaire d'inscription");
    private Text nom = new Text("Nom");
    private Text prenom = new Text("Prénom");
    private Text email = new Text("Email");
    private Text matricule = new Text("Matricule");
    private TextField nomField = new TextField();
    private TextField prenomField = new TextField();
    private TextField emailField = new TextField();
    private TextField matriculeField = new TextField();
    private TableView table = new TableView();
    private Separator sep1 = new Separator();
    private Separator sep2 = new Separator();
    private VBox left = new VBox();
    private VBox right = new VBox();
    private HBox hBox = new HBox();
    private HBox prenomBox = new HBox();
    private HBox nomBox = new HBox();
    private HBox emailBox = new HBox();
    private HBox matriculeBox = new HBox();


    public Vue() {
        HBox root = new HBox();
        root.getChildren().add(left);
        left.getChildren().add(ldc);
        left.getChildren().add(table);
        table.setEditable(true);
        TableColumn code = new TableColumn("Code");
        TableColumn cours = new TableColumn("Cours");
        left.getChildren().add(sep1);
        left.getChildren().add(hBox);
        hBox.getChildren().add(session);
        hBox.getChildren().add(charger);
        root.getChildren().add(right);
        root.getChildren().add(sep2);
        sep2.setOrientation(Orientation.VERTICAL);
        right.getChildren().add(fi);
        right.getChildren().add(prenomBox);
        prenomBox.getChildren().add(prenom);
        prenomBox.getChildren().add(prenomField);
        right.getChildren().add(nomBox);
        nomBox.getChildren().add(nom);
        nomBox.getChildren().add(nomField);
        right.getChildren().add(emailBox);
        emailBox.getChildren().add(email);
        emailBox.getChildren().add(emailField);
        right.getChildren().add(matriculeBox);
        matriculeBox.getChildren().add(matricule);
        matriculeBox.getChildren().add(matriculeField);
        right.getChildren().add(envoyer);
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }

    public Button getCharger() {
        return charger;
    }

    public Button getEnvoyer() {
        return envoyer;
    }

    public ChoiceBox getSession() {
        return session;
    }

    public void loadCourses() {
    }
}


