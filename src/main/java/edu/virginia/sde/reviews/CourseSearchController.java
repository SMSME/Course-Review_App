package edu.virginia.sde.reviews;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
    public TextField courseSubject;
    @FXML
    public TextField courseNumber;
    @FXML
    public TextField courseTitle;


    @FXML
    public VBox newCourseFields;
    @FXML
    public TextField newCourseSubject;
    @FXML
    public TextField newCourseNumber;
    @FXML
    public TextField newCourseTitle;


    @FXML
    public ListView<Course> courseListView;
    public List<Course> courses = new ArrayList<>();
    public Stage stage;
    public DatabaseDriver driver = DatabaseSingleton.getInstance();


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDriver(DatabaseDriver driver) {
        this.driver = driver;
    }

    public void clearNewCourses(){
        courseSubject.clear();
        courseNumber.clear();
        courseTitle.clear();
    }

    @FXML
    public void addCourse() throws SQLException {
        String subject = newCourseSubject.getText();
        String number = newCourseNumber.getText();
        String title = newCourseTitle.getText();
        if (validateCourse(subject,number,title)){
            Course newCourse = new Course(subject,Integer.parseInt(number),title);
            courses.add(newCourse);
            courseListView.getItems().setAll(courses);
            clearNewCourses();
            driver.addCourse(newCourse);
            toggleNewCourseFields();
        }else{
            //idk make a message appear
            System.out.println("Invalid data...");
        }
    }

    @FXML
    public boolean validateCourse(String subject, String number, String title){
        //all letters 2-4 in length
        if (!subject.matches("[A-Za-z]{2,4}")){
            return false;
        }
        //number that is exactly 4 in length
        if (!number.matches("\\d{4}")){
            return false;
        }
        //ensure at least one long and < 50 char
        if (title.isEmpty() || title.length()>50){
            return false;
        }
        return true;
    }


    @FXML
    public void createNewCourseButton() throws IOException, SQLException{
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Add New Course");

        dialog.getDialogPane().setMinSize(400,400);

        VBox dialogContent = new VBox(10);
        dialogContent.getChildren().addAll(
                new Label("New Course Subject"),
                newCourseSubject,
                new Label("New Course Number"),
                newCourseNumber,
                new Label("New Course Title"),
                newCourseTitle
        );

        dialog.getDialogPane().setContent(dialogContent);
        Platform.runLater(() -> newCourseSubject.requestFocus());

        ButtonType submitButton = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButton, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButton){
                try {
                    addCourse();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                //something to handle it idk yet
            }
            return null;
        });
        dialog.showAndWait();

    }

    @FXML
    public void toggleNewCourseFields(){
        newCourseFields.setVisible(!newCourseFields.isVisible());

        // Clear the fields when hiding
        if (!newCourseFields.isVisible()) {
            newCourseSubject.clear();
            newCourseNumber.clear();
            newCourseTitle.clear();
        }
    }



    @FXML
    public void handleSearch() throws IOException, SQLException {
        String subject = courseSubject.getText();
        String number = courseNumber.getText();
        String title = courseTitle.getText();

        List<Course> foundCourses = search(subject,number,title);
        courseListView.getItems().setAll(foundCourses);
    }

    public List<Course> search(String subject, String number, String title) throws SQLException {
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


