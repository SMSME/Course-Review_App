package edu.virginia.sde.reviews;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.sql.SQLException;
public class MyReviewsApplication extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("my-reviews.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        //DatabaseDriver driver = new DatabaseDriver("CruddyCoursework.sqlite");
        DatabaseDriver driver = DatabaseSingleton.getInstance();
        driver.connect();

        MyReviewsController controller = fxmlLoader.getController();
        controller.setStage(stage);
        //controller.setDriver(driver);

        stage.setTitle("My Reviews");
        stage.setScene(scene);
        stage.show();
    }
}
