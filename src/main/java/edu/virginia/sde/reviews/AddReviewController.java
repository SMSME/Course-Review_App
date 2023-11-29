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

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setDatabaseDriver(DatabaseDriver driver) {
        this.driver = driver;
    }
    @FXML
    public void addRating() {
        int rating = 0;
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
        }
        String comment = commentField.getText();
    }
}
