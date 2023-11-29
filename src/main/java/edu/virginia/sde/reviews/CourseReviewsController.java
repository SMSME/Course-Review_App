package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
public class CourseReviewsController implements Initializable{
    @FXML
    private Label courseLabel;
    private DatabaseDriver driver;
    private Stage stage;
    public void initialize(URL location, ResourceBundle resources) {
        setCourseLabel();
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setDatabaseDriver(DatabaseDriver driver) {
        this.driver = driver;
    }
    @FXML
    public void setCourseLabel() {
        courseLabel.setText("COURSE NAMaslfjoasoifjasdoifjsaisdjfaoisdE");
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
}
