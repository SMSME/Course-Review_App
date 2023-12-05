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
        try{
            reviews = driver.getReviewsFromUser(currentUser);
            if(reviews.isEmpty()){
                box.setVisible(true);
            }
            else{
                box.setVisible(false);
                reviewListView.setCellFactory(param -> new ListCell<>() {
                    @Override
                    protected void updateItem(Review item, boolean empty){
                        super.updateItem(item, empty);

                        if(empty || item == null){
                            setText(null);
                        } else{
                            String formattedReview = String.format("                                 " +
                                    " %s               " +
                                    " %d             " +
                                    " %d          %s", item.getCourseSubject(), item.getCourseNumber(), item.getRating(), item.getCourse().getCourseTitle());
                            setText(formattedReview);
                        }
                    }
                });
                reviewListView.getItems().setAll(reviews);
                reviewListView.setOnMouseClicked(this::handleReviewItemClick);
            }
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
                    controller.initializer();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


    @FXML
    private void onLogout() throws IOException, SQLException {
        driver.disconnect();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        LoginSceneController controller = fxmlLoader.getController();
        controller.setStage(stage);
    }

    @FXML
    private void backToCourseSearch() throws IOException, SQLException {
        driver.disconnect();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-search-screen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Course Search");

        CourseSearchController controller = fxmlLoader.getController();
        controller.setStage(stage);
    }
}
