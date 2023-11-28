package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
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
    private Stage stage;
    public void initialize(URL location, ResourceBundle resources) {
        setCourseLabel();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
    @FXML
    public void setCourseLabel() {
        System.out.println("lmao");
        courseLabel.setText("Hello");
    }
}
