package edu.virginia.sde.reviews;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

public class ReviewItemController {
    @FXML
    private Label courseMnemonic;
    @FXML
    private Label courseNumber;
    @FXML
    private Label courseRating;
    @FXML private Stage stage;


    private Course course;
    private Consumer<Course> clickHandler;
    public void setReviewData(Review review, Consumer<Course> clickHandler){
        this.course = review.getCourse();
        this.clickHandler = clickHandler;
        courseMnemonic.setText(review.getCourse().getCourseSubject());
        courseNumber.setText(String.valueOf(review.getCourse().getCourseNumber()));
        courseRating.setText(String.valueOf(review.getRating()));
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void handleClick(){
        if (clickHandler !=null){
            clickHandler.accept(course);
        }
    }

}
