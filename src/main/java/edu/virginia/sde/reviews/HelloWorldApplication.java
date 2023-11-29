package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class HelloWorldApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        DatabaseDriver d = new DatabaseDriver("CruddyCoursework.sqlite");
        d.connect();
        d.createTables();
        d.commit();
        d.disconnect();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-world.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello World");
        stage.setScene(scene);
        stage.show();
    }
}