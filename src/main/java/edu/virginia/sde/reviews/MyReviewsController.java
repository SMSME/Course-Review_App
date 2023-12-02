package edu.virginia.sde.reviews;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.SQLException;
public class MyReviewsController {
    @FXML
    private Label messageLabel;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    private DatabaseDriver driver;
    private User currentUser;
    @FXML
    private VBox myReviews;
    public void setStage(Stage stage){
        this.stage = stage;

    }

    //Need a method that will return all of the reviews that a giver user has written.
    //can use the user singleton to get the current user
    //currentUser = UserSingleton.getCurrentUser()
    //Will add it to the Vbox and print them out like matthew has them.

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
