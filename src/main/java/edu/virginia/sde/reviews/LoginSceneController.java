package edu.virginia.sde.reviews;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.SQLException;

public class LoginSceneController {
    @FXML
    private Label messageLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    private Stage stage;
    private DatabaseDriver driver;
    private String currentUser;
    public void setStage(Stage stage){
        this.stage = stage;
    }
    //@FXML
    @FXML
    private void initialize(){
        driver = DatabaseSingleton.getInstance();
        messageLabel.getStyleClass().add("error-label");
    }

    @FXML
    private void handleButton() throws SQLException, IOException{
        String user = usernameField.getText();
        String pass = passwordField.getText();
        //If a correct username/password entered
        try{
            if(UserSingleton.login(user, pass)!=null){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-search-screen.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Course Search");

                CourseSearchController controller = fxmlLoader.getController();
                controller.setStage(stage);
                messageLabel.setText("Login successful");
            }
        } catch (IllegalArgumentException e){
            messageLabel.setText("User not found");
        }
        catch(IllegalStateException e){
            messageLabel.setText("Password is incorrect");
        }
        catch(IOException e){
            messageLabel.setText("Login not successful, an error occurred.");
        }

    }

    @FXML
    private void handleCreateUser() throws IOException{
        //messageLabel.setText("Create new user button pressed");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("new-user.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Create New User");

        NewUserController newuserController = fxmlLoader.getController();
        newuserController.setStage(stage);
        //newuserController.setDriver(driver);
    }

    @FXML
    private void exitProgram(){
        try {
            driver.disconnect();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Platform.exit();
    }
    private boolean isValid(String username, String password) throws SQLException {
        //If the user has a password
        if(driver.getPassword(username)!=null){
            return password.equals(driver.getPassword(username));
        }
        return false;
    }
}
