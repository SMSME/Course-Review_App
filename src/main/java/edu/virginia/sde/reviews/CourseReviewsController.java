package edu.virginia.sde.reviews;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import static java.lang.Integer.parseInt;

public class CourseReviewsController {
    @FXML
    private Label courseLabel;
    @FXML
    private ListView<ReviewDisplay> reviewListView;

    private DatabaseDriver driver;
    private Stage stage;
    private Course currentCourse;
    private int newRating;
    private String newComment;
    @FXML
    private VBox newReviewFields;
    @FXML
    private TextField newReviewRating;
    @FXML
    private TextField newReviewComment;
    @FXML
    public void submitReview() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User currentUser = new User("matthews");
        Review newReview = new Review(currentCourse, newRating, timestamp, newComment, currentUser.getUsername());
        try {
            driver.addReview(newReview);
            driver.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void initializer() throws RuntimeException {
        List<Review> courseReviews;
        reviewListView = new ListView<>();
        try {
            courseReviews = driver.getReviewsFromCourse(currentCourse);
        } catch (SQLException e) {
            throw new RuntimeException("Runtime Exception");
        }

        ObservableList<ReviewDisplay> data = FXCollections.observableArrayList();

        for (Review review : courseReviews) {
            data.add(new ReviewDisplay(review.getRating(),review.getComment()));
        }
        reviewListView.setItems(data);
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
    public void addReview() throws SQLException {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Add New Review");

        dialog.getDialogPane().setMinSize(400,400);

        VBox dialogContent = new VBox(10);
        dialogContent.getChildren().addAll(
                new Label("Rating: "),
                newReviewRating,
                new Label("Comment: "),
                newReviewComment
        );
        dialog.getDialogPane().setContent(dialogContent);
        Platform.runLater(() -> newReviewRating.requestFocus());
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton){
                if (newReviewRating.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Rating");
                    alert.setContentText("Rating must be an integer from 1 to 5");
                    alert.setOnHidden(alertEvent -> dialog.showAndWait());
                    alert.showAndWait();
                }
                try {
                    newRating = parseInt(newReviewRating.getText());
                }
                catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Rating");
                    alert.setContentText("Rating must be an integer from 1 to 5");
                    alert.setOnHidden(alertEvent -> dialog.showAndWait());
                    alert.showAndWait();
                    return null;
                }
                newComment = newReviewComment.getText();
                if (newRating < 1 || newRating > 5) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Rating");
                    alert.setContentText("Rating must be an integer from 1 to 5");
                    alert.setOnHidden(alertEvent -> dialog.showAndWait());
                    alert.showAndWait();
                    return null;
                }
                else {
                    submitReview();
                    reviewListView.getItems().clear();
                    initializer();
                    dialog.close();
                }
            }
            return null;
        });
        dialog.showAndWait();
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
