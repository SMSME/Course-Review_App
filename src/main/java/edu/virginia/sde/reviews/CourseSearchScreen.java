package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CourseSearchScreen extends Application{
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-search.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Course Search");
        stage.setScene(scene);
        stage.show();
        }
    }

