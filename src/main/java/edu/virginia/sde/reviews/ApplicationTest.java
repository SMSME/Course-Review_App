package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.crypto.Data;

public class ApplicationTest extends Application{
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-reviews.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        DatabaseDriver driver = DatabaseSingleton.getInstance();
        driver.connect();

        CourseReviewsController controller = fxmlLoader.getController();
        controller.setStage(stage);

        Course tempCourse = new Course("CS",3200,"Data Structures 3");
        controller.setCurrentCourse(tempCourse);
        controller.initializer();

        stage.setTitle("Course Reviews");
        stage.setScene(scene);
        stage.show();
    }
}
