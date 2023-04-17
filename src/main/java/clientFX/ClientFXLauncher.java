package main.java.clientFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * La classe qui démarre le clientFX
 */

public class ClientFXLauncher extends Application {
    /**
     * Port sur lequel le client se connecte
     */
    public final static int PORT = 1337;
    /**
     * Adresse IP de connexion
     */
    public final static String ADDRESS = "127.0.0.1";

    /**
     * Méthode main de la classe clientFXLauncher
     *
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    /**
     * Méthode qui lance le client de l'interface graphique
     *
     * @param stage le stage de l'interface graphique
     * @throws Exception lancé dans le cas d'une exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Modele leModele = new Modele(ADDRESS,PORT);
        Vue laVue = new Vue();
        new Controleur(leModele, laVue);

        Scene scene = new Scene(laVue.getRoot(), 600, 400);

        stage.setScene(scene);
        stage.setTitle("Inscription UdeM");
        stage.show();
    }


}
