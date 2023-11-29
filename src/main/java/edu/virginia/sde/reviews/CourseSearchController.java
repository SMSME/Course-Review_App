package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.text.LabelView;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseSearchController {
    @FXML
    private TextField searchField;
    @FXML
    private Label messageLabel;
    private Stage stage;
    private DatabaseDriver driver;
    private String curUser;
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("your_fxml_file.fxml"));
    Parent root = fxmlLoader.load();
    CourseSearchController controller = fxmlLoader.getController();



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
    private void handleButtonClick(){

    }



//    private List<Course> findCourseByTitle(String title) throws SQLException {
//        DatabaseDriver db = new DatabaseDriver("CruddyCoursework.sqlite");
//        List<Course> foundCourses = db.getCoursesByName(title);
//        return foundCourses;
//        }
    }

