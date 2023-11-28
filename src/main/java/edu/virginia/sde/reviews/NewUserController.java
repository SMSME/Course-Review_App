package edu.virginia.sde.reviews;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    public void setInfo(DatabaseDriver driver){
        this.driver = driver;
    }
    public void createUser(){
        String newUser = newUsername.getText();
        String newPass = newPassword.getText();
        try{
            if(driver.getPassword(newUser)!=null){
                message.setText("User already exists");
            }
            if(newPass.length()<8){
                message.setText("Password must be at least 8 characters.");
            }
            if(isValid(newUser, newPass)){
                driver.addUser(newUser, newPass);
                message.setText("User Created Successfully!");
            }

        } catch (SQLException e){
            e.printStackTrace();
            message.setText("Error Creating user");
        }
    }
    private boolean isValid(String username, String password){
        return !username.isEmpty() && password.length()>=8;
    }
}
