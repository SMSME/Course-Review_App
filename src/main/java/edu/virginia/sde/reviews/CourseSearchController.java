package edu.virginia.sde.reviews;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

//make it so that if it has the same department/subject the number isnt the same/ titles arent the same -idk how to throw an error for this lol - ask jinwoo lol
//like how do i fix it brug
//add reviews for the user ?? - how do i know kek xD
//if u make the number 0000 it like

//add some formatting to make it look better too - LITERALLY ITS SO UGLy
//thers an error if u format it to capitalize the letters because if theres a space at the end it panics



public class CourseSearchController {
    //for searching the database
    @FXML
    public TextField courseSubject;
    @FXML
    public TextField courseNumber;
    @FXML
    public TextField courseTitle;




    //display courses
    @FXML
    public ListView<Course> courseListView;
    public Stage stage;
    public DatabaseDriver driver = DatabaseSingleton.getInstance();
    private boolean sceneClosed = false;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDriver (DatabaseDriver driver) {this.driver = driver;}


    public void initialize() throws SQLException {
        courseListView.setOnMouseClicked(this::handleCourseClick);
        driver.connect();
    }

    public void close() throws SQLException {
        try{
        if (driver!=null){
            driver.disconnect();}
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void setSceneClosed() throws SQLException {
        sceneClosed = true;
        close();
    }

    //connect after written
    @FXML
    public void myReviews() throws IOException{
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("my-reviews.fxml"));
//        Parent root = fxmlLoader.load();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.setTitle("My Reviews");
//        MyReviewsController myReviewsContoller = fxmlLoader.getController();
//        myReviewsContoller.setStage(stage);
//        myReviewsContoller.setDriver(driver);
    }

    @FXML
    public void logOut() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        LoginSceneController loginSceneController = fxmlLoader.getController();
        loginSceneController.setStage(stage);
        loginSceneController.setDriver(driver);
    }

    //dealing with basic course searching - shouldnt u make a button??
    public void clearNewCourses() {
        courseSubject.clear();
        courseNumber.clear();
        courseTitle.clear();
    }

    public void handleCourseClick(MouseEvent event){
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
            Course course = courseListView.getSelectionModel().getSelectedItem();
            if (course != null){
                try{
                    openCourseRev(course);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void openCourseRev(Course course) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-reviews.fxml"));
        Parent root = fxmlLoader.load();
        Scene currentScene = courseListView.getScene();
        currentScene.setRoot(root);

        CourseReviewsController crc = fxmlLoader.getController();
        crc.setStage(stage);
        crc.setDatabaseDriver(driver);
        crc.setCurrentCourse(course);
    }



    //adding a new course - check if valid
    @FXML
    public boolean checkTextfields(TextField subject, TextField number, TextField title) {
        // All fields must not be empty
        if (subject.getText().isEmpty() || number.getText().isEmpty() || title.getText().isEmpty()) {
            return false;
        }

        // All letters in the subject, 2-4 characters in length
        if (!subject.getText().matches("[A-Za-z]{2,4}")) {
            return false;
        }

        // Number must be exactly 4 digits
        if (!number.getText().matches("\\d{4}")) {
            return false;
        }

        // Title length should be less than or equal to 50 characters
        if (title.getText().length() > 50) {
            return false;
        }

        return true;
    }


    //forces you to only type lettesr / numbers for certain parts lol
    @FXML
    public TextFormatter<String> createTextFormat (String pattern){
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if(newText.matches(pattern)){
                return change;
            }else{
                return null;
            }
        };
        return new TextFormatter<>(filter);
    }


//    public boolean sameCourseNumandSub(Course newCourse) throws SQLException {
//        DatabaseDriver db = DatabaseSingleton.getInstance();
//        try {
//            db.connect();
//            List<Course> courses = db.getCourses();
//            for (Course course : courses) {
//                if (course.getCourseSubject().equalsIgnoreCase(newCourse.getCourseSubject()) && course.getCourseNumber() == newCourse.getCourseNumber()) {
//                    return true;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (db != null) {
//                db.disconnect();
//            }
//            return false;
//        }
//    }


    @FXML
    public void createNewCourse() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Add New Course");

        dialog.getDialogPane().setMinSize(400, 400);
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/Styles/CourseSearchController.css").toExternalForm());
        System.out.println("CSS File Loaded: " + getClass().getResource("/Styles/CourseSearchController.css"));


        TextField subjectField = new TextField();
        TextField numberField = new TextField();
        TextField titleField = new TextField();

        Label errorMessage = new Label();
        errorMessage.getStyleClass().add("error-label");

        subjectField.setTextFormatter(createTextFormat("[a-zA-Z]{0,4}"));
        numberField.setTextFormatter(createTextFormat("\\d{1,4}"));
        titleField.setTextFormatter(createTextFormat(".{0,50}"));

        VBox dialogContent = new VBox(10);
        dialogContent.getChildren().addAll(
                new Label("Course Subject"),
                subjectField,
                new Label("Course Number"),
                numberField,
                new Label("Course Title"),
                titleField,
                errorMessage
        );

        dialog.getDialogPane().setContent(dialogContent);
        Platform.runLater(() -> subjectField.requestFocus());

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                String subject = subjectField.getText().toUpperCase();
                String number = numberField.getText();
                String title = titleField.getText();

                try {
                    Course newCourse = new Course(subject, Integer.parseInt(number), title);
                    driver.addCourse(newCourse);
                    driver.commit();
                    updateCourseListView();
                    clearNewCourses();
                } catch (SQLException e) {
                    errorMessage.setText("Error in Adding Course. Please check your fields and try again.");
                    e.printStackTrace();
                }
            }
            return null;
        });
        //check if the fields are filled or not
        Node addButtonNode = dialog.getDialogPane().lookupButton(addButton);
        addButtonNode.addEventFilter(ActionEvent.ACTION, event -> {
            // Check whether some conditions are fulfilled
            if (!checkTextfields(subjectField, numberField, titleField)) {
                errorMessage.setText("Course is not valid. Please check your fields.");
                // The conditions are not fulfilled, so we consume the event
                // to prevent the dialog from closing
                event.consume();
            }
        });

        dialog.showAndWait();
    }




    //updates list for courses to appear
    @FXML
    public void updateCourseListView() {
        try {
            List<Course> allCourses = driver.getCourses();
            courseListView.getItems().setAll(allCourses);
        } catch (SQLException e) {
            System.out.println("Error in getting courses " + e.getMessage());
            e.printStackTrace();
        }
    }

    //searching database
    @FXML
    public void handleSearch() throws IOException, SQLException {
        String subject = courseSubject.getText();
        String number = courseNumber.getText();
        String title = courseTitle.getText();

        List<Course> foundCourses = search(subject, number, title);

        if (subject.isEmpty() && number.isEmpty() && title.isEmpty()) {
            updateCourseListView();
            courseListView.getItems().setAll(foundCourses);
        } else {
            courseListView.getItems().setAll(foundCourses);
        }
    }

    //ngl i think that this is supposed to be a database method since this is a controller class??
    public List<Course> search(String subject, String number, String title) throws SQLException {
        //refers to whether each search bar is filled in the order of subject, number, Title
        int searchbars = 0;
        List<Course> matchCourses = new ArrayList<>();
        List<Course> coursesbySub = new ArrayList<>();
        List<Course> coursesbyNum = new ArrayList<>();
        List<Course> coursesbyTitle = new ArrayList<>();


        try {

            if (title.isEmpty() && number.isEmpty() && subject.isEmpty()){
                matchCourses.addAll(driver.getCourses());
            }else {
                if (!subject.isEmpty()) {
                    coursesbySub.addAll(driver.getCoursesBySubject(subject));
                    searchbars += 1;
                }

                if (!number.isEmpty()) {
                    coursesbyNum.addAll(driver.getCoursesByNumber(Integer.parseInt(number)));
                    searchbars += 2;
                }

                if (!title.isEmpty()) {
                    coursesbyTitle.addAll(driver.getCoursesByName(title));
                    searchbars += 4;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


            /// case 1 = search by subject only
            // case 2 = search by number only
            // case 3 = search by subject and number
            // case 4 = search by Title only
            // case 5 = search by subject and Title
            // case 6 = search by number and Title
            // case 7 = search by subject, number, and Title
            switch (searchbars) {
                case 1:
                    matchCourses.addAll(coursesbySub);
                    break;
                case 2:
                    matchCourses.addAll(coursesbyNum);
                    break;
                case 3:
                    matchCourses.addAll(coursesbySub);
                    matchCourses.retainAll(coursesbyNum);
                    break;
                case 4:
                    matchCourses.addAll(coursesbyTitle);
                    break;
                case 5:
                    matchCourses.addAll(coursesbySub);
                    matchCourses.retainAll(coursesbyTitle);
                    break;
                case 6:
                    matchCourses.addAll(coursesbyTitle);
                    matchCourses.retainAll(coursesbyNum);
                    break;
                case 7:
                    matchCourses.addAll(coursesbySub);
                    matchCourses.retainAll(coursesbyNum);
                    matchCourses.retainAll(coursesbyTitle);
                    break;
            }
            return matchCourses;
        }


    }
