package edu.virginia.sde.reviews;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MyReviewsApplication extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        DatabaseDriver driver = DatabaseSingleton.getInstance();
        driver.connect();
        UserSingleton.login("jamtran12", "password4");
        Course course = new Course("DSA", 3100, "Data Structures and Algorithms 2");
        driver.addCourse(course);
        Timestamp timestamp = new Timestamp(2023, 12,3, 0,0,0,0);
        Review review = new Review(course, 4, timestamp, "good", "jamtran12");

        driver.addReview(review);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("my-reviews.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        MyReviewsController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.initialize();

        stage.setTitle("My Reviews");
        stage.setScene(scene);
        stage.show();
    }
}
