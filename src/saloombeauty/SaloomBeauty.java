/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saloombeauty;

import dataBase.DBConnection;
import dataBase.DBModel;
import dataBase.DBProperties;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Ralande
 */
public class SaloomBeauty extends Application {

    Properties properties = new Properties();
    InputStream inputStream;
    OutputStream output = null;

    public SaloomBeauty() throws FileNotFoundException, IOException {

        output = new FileOutputStream("database.properties");
        properties.setProperty("host", "localhost");
        properties.setProperty("port", "3306");
        properties.setProperty("db", "saloom_bdd");
        properties.setProperty("user", "root");
        properties.setProperty("password", "");
        properties.store(output, null);
        output.close();

        DBModel model = new DBModel();
        model.createDataBase();
    }

    private PreparedStatement pst;
    private Connection con;
    private ResultSet rs;
    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    @Override
    public void start(Stage stage) throws Exception {
        /*  Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();*/

        DBConnection dbCon = new DBConnection();
        con = dbCon.geConnection();
        if (con != null) {
            try {
                pst = con.prepareStatement("SELECT Id FROM " + db + ".User ORDER BY Id ASC LIMIT 1");
                rs = pst.executeQuery();
                if (rs.next()) {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
                    root.getStylesheets().add("../css/main.css");
                    Scene scene = new Scene(root);
                    stage.setTitle("Saloom Beauty - Login");
                    stage.setScene(scene);
                    stage.setMaximized(false);
                    stage.setResizable(false);
                    stage.getIcons().add(new Image("/image/lg.png"));
                    stage.show();
                } else {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/Registration.fxml"));
                    root.getStylesheets().add("../css/main.css");
                    Scene scene = new Scene(root);
                    stage.setTitle("Saloom Beauty - Registration");
                    stage.setScene(scene);
                    stage.setMaximized(false);
                    stage.setResizable(false);
                    stage.getIcons().add(new Image("/image/lg.png"));
                    stage.show();
                }
                con.close();
                pst.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(SaloomBeauty.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error : Server Not Found");
            alert.setContentText("Make sure your mysql is Start properly, \n");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
