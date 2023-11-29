package edu.virginia.sde.reviews;

import javafx.stage.Stage;

public class AddReviewController {
    private DatabaseDriver driver;
    private Stage stage;
    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setDatabaseDriver(DatabaseDriver driver) {
        this.driver = driver;
    }
}
