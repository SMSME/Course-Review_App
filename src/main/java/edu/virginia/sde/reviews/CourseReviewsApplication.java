package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CourseReviewsApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("testing.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Course Reviews");
        stage.setScene(scene);
        stage.show();
    }
}
