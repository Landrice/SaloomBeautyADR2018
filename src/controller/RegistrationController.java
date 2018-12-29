/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAL.Users;
import Getway.UsersGetway;
import dataBase.DBProperties;
import dataBase.SQL;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Ralande
 */
public class RegistrationController implements Initializable {

    @FXML
    private TextField tfUserName;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField pfPass;
    @FXML
    private PasswordField pfRePass;
    @FXML
    private TextField tfFullName;
    @FXML
    private TextArea taInfo;
    @FXML
    private Button btnSignUp;
    Users users = new Users();
    UsersGetway usersGetway = new UsersGetway();
    
    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    private Stage stage;

    private PreparedStatement pst;
    private Connection con;
    private ResultSet rs;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tfUserName.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.isEmpty()) {
                if (!newValue.matches("^[^\\d\\s]+$")) {
                    tfUserName.setText(oldValue);
                }
            }
        });
    }    

    @FXML
    private void signUpOnAction(ActionEvent event) {
        SQL sql = new SQL();
         if (isValid() && isPassMatch() && isValidEmail()) {
                         users.userName = tfUserName.getText();
            users.fullName = tfUserName.getText();
            users.password = pfRePass.getText();
            usersGetway.save(users);
            sql.basicPermission(tfUserName.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Now");
            alert.setHeaderText("Login now");
            alert.setContentText("You admin account has been create sucessfully \n to login now click ok");
            alert.initStyle(StageStyle.UNDECORATED);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    h1Login();
                } catch (IOException ex) {
                    Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
         }
    }
    
    public void h1Login() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        Scene scene = new Scene(root);
        Stage nStage = new Stage();
        nStage.setScene(scene);
        nStage.setMaximized(true);
        nStage.setTitle("Registration -Saloom Beauty");
        nStage.setMaximized(false);
        nStage.setResizable(false);
        nStage.show();

        Stage hlLoginStage = (Stage) btnSignUp.getScene().getWindow();
        hlLoginStage.close();
    }
    private boolean isValid() {
        boolean valid = true;
        if (tfFullName.getText().isEmpty() || tfEmail.getText().isEmpty() || tfUserName.getText().isEmpty() || pfPass.getText().isEmpty() || pfRePass.getText().isEmpty() || taInfo.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("All field required");
            alert.setHeaderText("All firleds are required");
            alert.setContentText("All text fields are required. please fill all text field");
            alert.show();
            valid = false;
        }
        return valid;
    }
    private boolean isPassMatch() {
        boolean valid = false;
        if (pfPass.getText().matches(pfRePass.getText())) {
            valid = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password not match");
            alert.setHeaderText("Password not match");
            alert.show();
        }

        return valid;
    }

    private boolean isValidEmail() {
        boolean valid = false;
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(tfEmail.getText());
        if (matcher.find()) {
            valid = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Email not valid");
            alert.setHeaderText("Email address is not valid");
            alert.setContentText("Email address is not valid please enter a valid email address");
            alert.show();
            valid = false;
        }
        return valid;
    }
}
