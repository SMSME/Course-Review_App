package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class CourseReviewsController {
    private Label courseLabel;
    public void setCourseLabel(String newText) {
        courseLabel.setText(newText);
    }
}
