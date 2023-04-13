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
import javafx.util.Pair;
import main.java.server.models.Course;



import java.util.ArrayList;

public class Vue {

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
    private TableView<Course> table = new TableView<Course>();
    private Separator sep1 = new Separator();
    private Separator sep2 = new Separator();
    private VBox left = new VBox();
    private VBox right = new VBox();
    private HBox hBox = new HBox();
    private HBox prenomBox = new HBox();
    private HBox nomBox = new HBox();
    private HBox emailBox = new HBox();
    private HBox matriculeBox = new HBox();
    private TableColumn codeColumn = new TableColumn("Code");
    private TableColumn coursColumn = new TableColumn("Cours");
    private HBox root = new HBox();




    public Vue() {
        root.getChildren().add(left);
        left.getChildren().add(ldc);
        ldc.setTextAlignment(TextAlignment.CENTER);
        left.getChildren().add(table);
        table.setEditable(true);
        table.getColumns().addAll(codeColumn, coursColumn);
        left.getChildren().add(sep1);
        left.getChildren().add(hBox);
        hBox.getChildren().add(session);
        hBox.getChildren().add(charger);
        root.getChildren().add(right);
        root.getChildren().add(sep2);
        sep2.setOrientation(Orientation.VERTICAL);
        right.getChildren().add(fi);
        fi.setTextAlignment(TextAlignment.CENTER);
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
        BackgroundFill background_fill = new BackgroundFill(Color.YELLOW,CornerRadii.EMPTY, Insets.EMPTY);
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
        table.getColumns().addAll(codeColumn,coursColumn);
     }
    }



