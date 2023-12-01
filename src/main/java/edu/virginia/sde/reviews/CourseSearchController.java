package edu.virginia.sde.reviews;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public Stage stage;
    public DatabaseDriver driver = new DatabaseDriver("CruddyCoursework.sqlite");


    public void setStage(Stage stage) {
        this.stage = stage;
    }


    //dealing with basic course searching
    public void clearNewCourses(){
        courseSubject.clear();
        courseNumber.clear();
        courseTitle.clear();
    }

    //adding a new course - check if valid
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

    //ensure all fields are filled when adding new course to review
    @FXML
    public boolean fieldsFilled(){
        return !newCourseSubject.getText().isEmpty() && !newCourseNumber.getText().isEmpty()
                && !newCourseTitle.getText().isEmpty();
    }

    //add course into database and update the display
    @FXML
    public void addCourse(){
        String subject = newCourseSubject.getText().toLowerCase();
        String number = newCourseNumber.getText();
        String title = newCourseTitle.getText();

        if (validateCourse(subject,number,title)){
            Course newCourse = new Course(subject,Integer.parseInt(number),title);
            try{
                driver.addCourse(newCourse);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            updateCourseListView();
            clearNewCourses();
            toggleNewCourseFields();
        }else{
            //idk make a message appear
            System.out.println("Invalid data...");
        }
    }

    //create new course - opens a dialog box to fill
    @FXML
    public void createNewCourse() throws IOException, SQLException{
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

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton){
                if (fieldsFilled()){
                    addCourse();
                    dialog.close();}
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Incomplete Form");
                    alert.setContentText("Please fill in all fields");

                    alert.setOnHidden(alertEvent -> dialog.showAndWait());
                    alert.showAndWait();

                    return null;
                }
                //something to handle it idk yet
            }
            return null;
        });

        dialog.showAndWait();
    }

    //changes visibility with new course fields
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

    //updates list for courses to appear
    @FXML
    public void updateCourseListView(){
        try{
            List<Course> allCourses = driver.getAllCourses();
            courseListView.getItems().setAll(allCourses);
        } catch (SQLException e){
            System.out.println("Error in getting courses idk bro");
        }
    }

    //searching database
    @FXML
    public void handleSearch() throws IOException, SQLException {
        String subject = courseSubject.getText();
        String number = courseNumber.getText();
        String title = courseTitle.getText();

        List<Course> foundCourses = search(subject,number,title);

        if (subject.isEmpty() && number.isEmpty() && title.isEmpty()){
            updateCourseListView();
        }else{
            courseListView.getItems().setAll(foundCourses);
        }
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