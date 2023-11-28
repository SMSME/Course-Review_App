package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.text.LabelView;

public class CourseSearchController {
    @FXML
    private TextField searchField;
    @FXML
    private Label messageLabel;
    @FXML
    private void handleSearch(){
        String course = searchField.getText();
        messageLabel.setText("Getting results for" + course);
    }
}
