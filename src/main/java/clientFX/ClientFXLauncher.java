package main.java.clientFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientFXLauncher extends Application {

    public final static int PORT = 1337;
    public final static String ADDRESS = "127.0.0.1";

    public static void main(String[] args) throws IOException {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Modele leModele = new Modele(ADDRESS,PORT);
        Vue laVue = new Vue();
        new Controleur(leModele, laVue);

        Scene scene = new Scene(laVue.getRoot(), 350, 200);

        stage.setScene(scene);
        stage.setTitle("Inscription UdeM");
        stage.show();
    }


}
