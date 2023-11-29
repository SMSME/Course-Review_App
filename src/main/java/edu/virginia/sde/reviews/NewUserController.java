package edu.virginia.sde.reviews;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.Scene;
public class NewUserController {
    @FXML
    private Label message;
    @FXML
    private TextField newUsername;
    @FXML
    private PasswordField newPassword;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    private DatabaseDriver driver;

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setDriver(DatabaseDriver driver){
        this.driver = driver;
    }
    public void createUser() throws SQLException{
        String newUser = newUsername.getText();
        String newPass = newPassword.getText();

        //If they entered a valid username and password
        if (isValid(newUser, newPass)) {
            // If the username already exists or the password is too short
            if (driver.getPassword(newUser) != null) {
                message.setText("User already exists");

            }
            else{
                // If the username and password are valid, create the user
                driver.addUser(newUser, newPass);
                driver.commit();
                message.setText("User Created Successfully! Please return to the login page");
            }
        }
        //If they enter an invalid username password combo
        else {
            //The password is not long enough.
            if(newPass.length()<8){
                message.setText("Password must be at least 8 characters.");
            }

        }
    }
    private boolean isValid(String username, String password){
        return !username.isEmpty() && password.length()>=8;
    }
    @FXML
    private void exitProgram(){
        Platform.exit();
    }
    @FXML
    private void backToLogin() throws IOException{
        //try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            LoginSceneController controller = fxmlLoader.getController();
            controller.setDriver(driver);
            controller.setStage(stage);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }
}
