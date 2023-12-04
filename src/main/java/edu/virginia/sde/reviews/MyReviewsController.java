package edu.virginia.sde.reviews;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyReviewsController {
    @FXML
    private Label messageLabel;
    @FXML
    private Label courseTitleLabel;
    @FXML
    private Label courseSubjectLabel;
    @FXML
    private Label courseNumberLabel;
    @FXML
    private TableView<Review> reviewTableView;
    @FXML
    private TableColumn<Review,Integer> ratingColumn;
    @FXML
    private TableColumn<Review,String> mnemonicColumn;
    @FXML
    private TableColumn<Review, Integer> numberColumn;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private ListView<Review> reviewListView;
    private DatabaseDriver driver;
    private String currentUser;
    @FXML
    private VBox box;
    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void initialize() throws SQLException {
        currentUser = UserSingleton.getCurrentUser().getUsername();
        driver = DatabaseSingleton.getInstance();
        List<Review> reviews;
//        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
//        mnemonicColumn.setCellValueFactory(new PropertyValueFactory<>("courseSubject"));
//        numberColumn.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));
        try{
            reviews = driver.getReviewsFromUser(currentUser);
            driver.disconnect();
            System.out.println("Number of reviews: " + reviews.size());
            for(Review review: reviews){
                System.out.println(review.getCourse().toString());
                System.out.println(review.getUser());
                System.out.println(review.getRating());
                System.out.println(review.getCourse().getCourseSubject());
            }
            if(reviews.isEmpty()){
                box.setVisible(true);
            }
            else{
                box.setVisible(false);
                reviewListView.getItems().setAll(reviews);
                reviewListView.setOnMouseClicked(this::handleReviewItemClick);
            }
            //ObservableList<Review> reviewList = FXCollections.observableArrayList(reviews);
            //ObservableList<Course> courseList = FXCollections.observableArrayList(courses);
            //reviewTableView.setItems(reviewList);
//            for(Review review: reviews){
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("review-item.fxml"));
//                Parent root = loader.load();
//
//                ReviewItemController controller = loader.getController();
//                controller.setReviewData(review, this:: handleReviewItemClick);
//                myReviews.getChildren().add(root);
//
//            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void handleReviewItemClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
            Review review = reviewListView.getSelectionModel().getSelectedItem();
            if (review != null){
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("course-reviews.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Course Reviews");
                    CourseReviewsController controller = loader.getController();
                    controller.setCurrentCourse(review.getCourse());
                    controller.setStage(stage);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

//    private void handleReviewItemClick(Course course) {
//        try{
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("course-reviews.fxml"));
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.setTitle("Course Reviews");
//            CourseReviewsController controller = loader.getController();
//            controller.setCurrentCourse(course);
//            controller.setStage(stage);
//            //Shouldn't need this if database singleton is used properly.
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//
//    }

    @FXML
    private void onLogout() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        LoginSceneController controller = fxmlLoader.getController();
        controller.setDriver(driver);
        controller.setStage(stage);
    }

    @FXML
    private void backToCourseSearch() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-search-screen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Course Search");

        CourseSearchController controller = fxmlLoader.getController();
        controller.setStage(stage);
    }
}
