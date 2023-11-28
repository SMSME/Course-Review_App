package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.text.LabelView;

public class CourseSearchController {
    @FXML
    private TextField searchField;
    @FXML
    private Label messageLabel;
    private Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void handleSearch(){
        String course = searchField.getText();
        messageLabel.setText("Getting results for " + course);
    }

}
