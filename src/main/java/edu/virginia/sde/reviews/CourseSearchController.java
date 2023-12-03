package edu.virginia.sde.reviews;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

//make it so that if it has the same department/subject the number isnt the same/ titles arent the same -idk how to throw an error for this lol
//like how do i fix it brug
//add reviews for the user ?? - how do i know kek xD
//why everyony

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

    @FXML
    private Label errormessage;


    //display courses
    @FXML
    public ListView<Course> courseListView;
    public Stage stage;
    public DatabaseDriver driver = DatabaseSingleton.getInstance();


    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void initialize(){
        courseListView.setOnMouseClicked(this::handleCourseClick);
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
        Scene scene = new Scene(root);
        Stage courseReviewStage = new Stage();
        courseReviewStage.setScene(scene);
        courseReviewStage.setTitle("Course Review");

        CourseReviewsController crc = fxmlLoader.getController();
        crc.setStage(courseReviewStage);
        crc.setDatabaseDriver(driver);
        crc.setCurrentCourse(course);

        courseReviewStage.show();


    }



    //adding a new course - check if valid
    @FXML
    public boolean validateCourse(String subject, String number, String title) {
        //display an error if the course is invalid DO
        //all letters 2-4 in length
        //if there is a class with the same subject and number dont allow
        if (!subject.matches("[A-Za-z]{2,4}")) {
            return false;
        }
        //number that is exactly 4 in length
        if (!number.matches("\\d{4}")) {
            return false;
        }
        //ensure at least one long and < 50 char
        if (title.isEmpty() || title.length() > 50) {
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

    //ensure all fields are filled when adding new course to review
    @FXML
    public boolean fieldsFilled(TextField newCourseSubject, TextField newCourseNumber, TextField newCourseTitle) {
        return !newCourseSubject.getText().isEmpty() && !newCourseNumber.getText().isEmpty()
                && !newCourseTitle.getText().isEmpty();
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

    //add course into database and update the display
    @FXML
    public void addCourse(String subject, String number, String title) throws SQLException {
        DatabaseDriver db = DatabaseSingleton.getInstance();

        try {
            db.connect();
            if (validateCourse(subject, number, title)) {
                Course newCourse = new Course(subject, Integer.parseInt(number), title);
                //same course search bruh idk
                if (!db.courseExists(newCourse)) {
                    db.addCourse(newCourse);

                    // Commit the transaction
                    db.commit();
                    updateCourseListView();
                    clearNewCourses();
                } else {
                    // idk make a message appear
                    errormessage.setText("Course with same number and subject already exist");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.disconnect();
            }
        }
    }


    //create new course - opens a dialog box to fill
    @FXML
    public void createNewCourse() throws IOException, SQLException {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Add New Course");

        dialog.getDialogPane().setMinSize(400, 400);

        // Create the UI elements for the new course fields
        TextField newCourseSubjectField = new TextField();
        TextField newCourseNumberField = new TextField();
        TextField newCourseTitleField = new TextField();

        newCourseSubjectField.setTextFormatter(createTextFormat("[a-zA-Z]{0,4}"));
        newCourseNumberField.setTextFormatter(createTextFormat("\\d{0,4}"));
        newCourseTitleField.setTextFormatter(createTextFormat(".{0,50}"));



        VBox dialogContent = new VBox(10);
        dialogContent.getChildren().addAll(
                new Label("New Course Subject"),
                newCourseSubjectField,
                new Label("New Course Number"),
                newCourseNumberField,
                new Label("New Course Title"),
                newCourseTitleField,
                errormessage
        );

        dialog.getDialogPane().setContent(dialogContent);
        Platform.runLater(() -> newCourseSubjectField.requestFocus());

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                // Access the values from the dynamically created fields
                String subject = newCourseSubjectField.getText().toUpperCase();
                String number = newCourseNumberField.getText();
                String title = newCourseTitleField.getText();

                if (fieldsFilled(newCourseSubjectField, newCourseNumberField, newCourseTitleField)) {
                    // Add the course to the database and update the list view
                    try {
                        addCourse(subject, number, title);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    errormessage.setText("Already Exists");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Incomplete Form");
                    alert.setContentText("Please fill in all fields");

                    alert.setOnHidden(alertEvent -> dialog.showAndWait());
                    alert.showAndWait();

                    return null;
                }
            }
            return null;
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
            System.out.println("Error in getting courses idk bro");
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

    public List<Course> search(String subject, String number, String title) throws SQLException {
        //refers to whether each search bar is filled in the order of subject, number, Title
        int searchbars = 0;
        List<Course> matchCourses = new ArrayList<>();
        List<Course> coursesbySub = new ArrayList<>();
        List<Course> coursesbyNum = new ArrayList<>();
        List<Course> coursesbyTitle = new ArrayList<>();


        DatabaseDriver db = DatabaseSingleton.getInstance();
        try {
            db.connect();
            if (title.isEmpty() && number.isEmpty() && subject.isEmpty()){
                matchCourses.addAll(db.getCourses());
            }else {
                if (!subject.isEmpty()) {
                    coursesbySub.addAll(db.getCoursesBySubject(subject));
                    searchbars += 1;
                }

                if (!number.isEmpty()) {
                    coursesbyNum.addAll(db.getCoursesByNumber(Integer.parseInt(number)));
                    searchbars += 2;
                }

                if (!title.isEmpty()) {
                    coursesbyTitle.addAll(db.getCoursesByName(title));
                    searchbars += 4;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.disconnect();
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
}