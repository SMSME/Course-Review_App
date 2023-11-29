package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.text.LabelView;
import java.sql.SQLException;
import java.util.List;

public class CourseSearchController {
    @FXML
    private TextField searchField;
    @FXML
    private Label messageLabel;
    private Stage stage;
    private DatabaseDriver driver;
    private String curUser;


    public void setUser(String curUser){
        this.curUser = curUser;
    }

    public String getUser(){
        return curUser;
    }


    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setDriver(DatabaseDriver driver) {this.driver = driver;}

    @FXML
    private void handleSearch(){
        String course = searchField.getText();
        messageLabel.setText("Getting results for " + course);
    }

    private List<Course> findCourseByTitle(String title) throws SQLException {
        DatabaseDriver db = new DatabaseDriver();
        List<Course> foundCourse = db.getCoursesByName(title);
    }

}
