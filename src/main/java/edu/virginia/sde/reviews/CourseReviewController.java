package edu.virginia.sde.reviews;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
public class CourseReviewController {
    @FXML
    private Label messageLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    @FXML
    private void handleButton(){
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if(isValid(user,pass)){
            messageLabel.setText("Login successful");
        }
        else{messageLabel.setText("Login not successful");}
    }

    @FXML
    private void handleCreateUser(){
        messageLabel.setText("Create new user button pressed");
    }
    private boolean isValid(String username, String password){
        if (!username.isEmpty() && !password.isEmpty()){
            return true;
        }
        return false;
    }
}
