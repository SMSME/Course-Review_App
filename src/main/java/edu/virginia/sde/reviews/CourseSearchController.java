package edu.virginia.sde.reviews;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CourseSearchController {
    @FXML
    private TextField CourseSubject;
    @FXML
    private TextField CourseNumber;
    @FXML
    private TextField CourseTitle;
    @FXML
    private Label messageLabel;

    @FXML
    private ListView<Course> courseListView;
    private Stage stage;
    private DatabaseDriver driver;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDriver(DatabaseDriver driver) {
        this.driver = driver;
    }


    @FXML
    private void initialize() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-search-screen.fxml"));
        Parent root = fxmlLoader.load();
        CourseSearchController controller = fxmlLoader.getController();
        courseListView = new ListView<>();
    }

    @FXML
    private void createNewCourse() throws IOException, SQLException{

    }

    @FXML
    private void handleSearch() throws IOException, SQLException {
        DatabaseDriver driver = DatabaseSingleton.getInstance();

        String subject = CourseSubject.getText();
        String number = CourseNumber.getText();
        String title = CourseTitle.getText();

        List<Course> foundCourses = search(subject,number,title);
        courseListView.getItems().setAll(foundCourses);
    }

    private List<Course> search(String subject, String number, String title) throws SQLException {
        DatabaseDriver db = new DatabaseDriver("CruddyCoursework.sqlite");
        //refers to whether each search bar is filled in the order of subject, number, Title
        int searchbars = 0;

        List<Course> coursesbySub = new ArrayList<>();
        List<Course> coursesbyNum = new ArrayList<>();
        List<Course> coursesbyTitle = new ArrayList<>();
        List<Course> matchCourses = new ArrayList<>();

        if (!subject.isEmpty()) {
            coursesbySub.addAll(driver.getCoursesBySubject(subject));
            searchbars += 1;}

        if (!number.isEmpty()) {
            coursesbyNum.addAll(driver.getCoursesByNumber(Integer.parseInt(number)));
            searchbars += 2;}

        if (!title.isEmpty()) {
            coursesbyTitle.addAll(driver.getCoursesByName(title));
            searchbars += 4;}

        /// case 1 = search by subject only
        // case 2 = search by number only
        // case 3 = search by subject and number
        // case 4 = search by Title only
        // case 5 = search by subject and Title
        // case 6 = search by number and Title
        // case 7 = search by subject, number, and Title
       switch (searchbars){
           case 1:
               matchCourses.addAll(coursesbySub);
               break;
           case 2:
               matchCourses.addAll(coursesbyNum);
               break;
           case 3:
               for (Course course : coursesbySub){
                   if (coursesbyNum.contains(course)){
                       matchCourses.add(course);}}
               break;
           case 4:
               matchCourses.addAll(coursesbyTitle);
               break;
           case 5:
               for (Course course : coursesbySub){
                   if (coursesbyTitle.contains(course)){
                       matchCourses.add(course);}}
               break;
           case 6:
               for (Course course : coursesbyTitle){
                   if (coursesbyNum.contains(course)){
                       matchCourses.add(course);}}
               break;
           case 7:
               for (Course course : coursesbyTitle){
                   if (coursesbyNum.contains(course) && coursesbySub.contains(course)){
                       matchCourses.add(course);}}
               break;
       }
        return matchCourses;
    }


}


