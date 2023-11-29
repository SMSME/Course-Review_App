package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class ApplicationTest extends Application{
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-reviews.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        DatabaseDriver driver = new DatabaseDriver("CruddyCoursework.sqlite");

        CourseReviewsController controller = fxmlLoader.getController();
        controller.setStage(stage);


        controller.setDatabaseDriver(driver);
        stage.setTitle("Course Reviews");
        stage.setScene(scene);
        stage.show();
    }
}
