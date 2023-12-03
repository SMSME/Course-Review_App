package edu.virginia.sde.reviews;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MyReviewsController {
    @FXML
    private Label messageLabel;
    @FXML
    private ListView<ReviewDisplay> reviewListView;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    private DatabaseDriver driver;
    private String currentUser;
    @FXML
    private VBox myReviews;
    public void setStage(Stage stage){
        this.stage = stage;

    }

    //Need a method that will return all of the reviews that a giver user has written.
    //can use the user singleton to get the current user
    //currentUser = UserSingleton.getCurrentUser()
    //Will add it to the Vbox and print them out like matthew has them.
    public void initialize() throws SQLException {
        currentUser = UserSingleton.getCurrentUser().getUsername();
        driver = DatabaseSingleton.getInstance();
        List<Review> reviews;
        try{
            reviews = driver.getReviewsFromUser(currentUser);
            for(Review review: reviews){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("review-item.fxml"));
                Parent root = loader.load();

                ReviewItemController controller = loader.getController();
                controller.setReviewData(review, this:: handleReviewItemClick);
                myReviews.getChildren().add(root);

            }
        }catch(SQLException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void handleReviewItemClick(Course course) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("course-reviews.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Course Reviews");
            CourseReviewsController controller = loader.getController();
            controller.setCurrentCourse(course);
            controller.setStage(stage);
            //Shouldn't need this if database singleton is used properly.
            controller.setDatabaseDriver(driver);
        } catch(IOException e){
            e.printStackTrace();
        }

    }

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
