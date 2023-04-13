package main.java.clientFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Vue laVue = new Vue();

        Scene scene = new Scene(laVue.getRoot(), 350, 200);

        stage.setScene(scene);
        stage.setTitle("Inscription UdeM");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

