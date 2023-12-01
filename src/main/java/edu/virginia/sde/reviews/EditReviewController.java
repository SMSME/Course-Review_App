package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class EditReviewController {
    private DatabaseDriver driver;
    private Stage stage;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField commentField;
    @FXML
    private Label message;

    public void setStage(Stage stage){ this.stage = stage; }
    public void setDatabaseDriver(DatabaseDriver driver) { this.driver = driver; }
    public void editReview() throws SQLException {
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
            message.setText("Review successfully updated!");
            //driver.addReview(int rating, Timestamp timestamp, String comment);
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
