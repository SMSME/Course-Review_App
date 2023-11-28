package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CourseSearchScreen {

        public void start(Stage stage) throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-world.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello World");
            stage.setScene(scene);
            stage.show();
        }
    }
}
