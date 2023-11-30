package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
public class CourseReviewsController {
    @FXML
    private Label courseLabel;
    @FXML
    private VBox reviews;
    private DatabaseDriver driver;
    private Stage stage;
    private Course currentCourse;
    @FXML
    public void xd() throws RuntimeException {
        List<Review> courseReviews;
        try {
            courseReviews = driver.getReviewsFromCourse(currentCourse);
        } catch (SQLException e) {
            throw new RuntimeException("Runtime Exception");
        }
        for (Review review : courseReviews) {
            System.out.println(review.getRating());
            Label reviewRating = new Label();
            reviewRating.setText(String.valueOf(review.getRating()));
            Label reviewComment = new Label();
            reviewComment.setText(review.getComment());
            reviews.getChildren().add(reviewRating);
            reviews.getChildren().add(reviewComment);
        }
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setDatabaseDriver(DatabaseDriver driver) {
        this.driver = driver;
    }

    public void setCurrentCourse(Course currentCourse) {
        this.currentCourse = currentCourse;
        courseLabel.setText(currentCourse.getCourseTitle());
    }
    @FXML
    public void addReview() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-review.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add Review");

        AddReviewController addReviewController = fxmlLoader.getController();
        addReviewController.setStage(stage);
        addReviewController.setDatabaseDriver(driver);
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

    @FXML
    public void editReview() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit-review.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Edit Review");

        EditReviewController editReviewController = fxmlLoader.getController();
        editReviewController.setStage(stage);
        editReviewController.setDatabaseDriver(driver);
    }
}
