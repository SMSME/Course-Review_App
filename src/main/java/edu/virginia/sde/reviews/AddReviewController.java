package edu.virginia.sde.reviews;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javafx.scene.Scene;
import javafx.fxml.Initializable;
import static java.lang.Integer.parseInt;

public class AddReviewController {
    private DatabaseDriver driver;
    private Stage stage;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField commentField;
    @FXML
    private Label message;
    @FXML
    private Course currentCourse;

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setDatabaseDriver(DatabaseDriver driver) {
        this.driver = driver;
    }
    @FXML
    public void addRating() throws SQLException {
        int rating = 0;
        String comment = commentField.getText();
        try {
            rating = parseInt(ratingField.getText());
        }
        catch (NumberFormatException e) {
            message.setText("Rating must be an integer from 1 to 5");
        }
        if (rating < 1 || rating > 5 ) {
            message.setText("Rating must be an integer from 1 to 5");
        }
        else {
            message.setText("Review successfully added!");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            User currentUser = UserSingleton.getCurrentUser();
            Review newReview = new Review(currentCourse, rating, timestamp, comment, currentUser.getUsername());
            driver.addReview(newReview);
            driver.commit();
        }
    }
    @FXML
    public void backButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-reviews.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Course Reviews");

        CourseReviewsController courseReviewsController = fxmlLoader.getController();
        courseReviewsController.setStage(stage);
        courseReviewsController.setDatabaseDriver(driver);
    }

    @FXML
    public void logOut() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Course Reviews");
        LoginSceneController loginSceneController = fxmlLoader.getController();
        loginSceneController.setStage(stage);
        loginSceneController.setDriver(driver);
    }
}
