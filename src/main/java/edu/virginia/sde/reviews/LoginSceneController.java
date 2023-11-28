package edu.virginia.sde.reviews;
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

    public void setStage(Stage stage){
        this.stage = stage;
    }
    @FXML
    private void initialize(){
        try{
            driver = new DatabaseDriver("CruddyCoursework.sqlite");
            driver.connect();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void handleButton() throws SQLException{
        String user = usernameField.getText();
        String pass = passwordField.getText();

        //If a correct username/password entered
        if(isValid(user,pass)){
            messageLabel.setText("Login successful");
        }
        //If an incorrect username is entered
        else if(driver.getPassword(user)==null){
            messageLabel.setText("Username not found");
        }
        //password incorrect

        else{messageLabel.setText("Login not successful");}
    }

    @FXML
    private void handleCreateUser(){
        messageLabel.setText("Create new user button pressed");
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("new-user.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Create New User");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private boolean isValid(String username, String password) throws SQLException {
        //If the user has a password
        if(driver.getPassword(username) != null){
            if(password.equals(driver.getPassword(username))){
                return true;
            }
            return false;
        }
        //if (!username.isEmpty() && password.length()>=8){
        //    return true;
        //}
        return false;
    }
}
