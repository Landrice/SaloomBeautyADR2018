/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.stock;

import BLL.CustomerBLL;
import DAL.Customer;
import DAL.Supplyer;
import Getway.CustomerGetway;
import Getway.SupplyerGetway;
import com.jfoenix.controls.JFXDatePicker;
import custom.EffectUtility;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import media.userNameMedia;

/**
 * FXML Controller class
 *
 * @author Ralande
 */
public class AddCustomerTypeController implements Initializable {

    @FXML
    private AnchorPane apContent;
    @FXML
    private TextField tfSupplyerName;
    @FXML
    private Button btnSave;
    @FXML
    public Button btnUpdate;
    @FXML
    private Label lblCaption;
    @FXML
    private Button btnClose;
    private String usrId;

    public String supplyerId;

    private userNameMedia media;
    @FXML
    private JFXDatePicker tfDate;
    @FXML
    private TextField tfDS;
    @FXML
    private TextField tfDP;
    public userNameMedia getMedia() {
        return media;
    }

    public void setMedia(userNameMedia media) {
        usrId = media.getId();
        this.media = media;
    }

    Customer customer = new Customer();
        CustomerGetway customerGetway = new CustomerGetway();
    CustomerBLL customerBLL = new CustomerBLL();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tfDate.setValue(LocalDate.now());
        tfDS.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    tfDS.setText(oldValue);
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
        tfDP.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    tfDP.setText(oldValue);
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
    }    

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        if (isNotNull()) {
            customer.customertype=tfSupplyerName.getText();
            customer.date=String.valueOf(tfDate.getValue());
            customer.userId=usrId;
            customer.customerdiscountproducts=tfDP.getText();
            customer.customerdiscountservice=tfDS.getText();
            customerBLL.saveType(customer);
            clrAll();
        }
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnUpdate.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void apOnMouseDragged(MouseEvent event) {
    }

    @FXML
    private void apOnMousePressed(MouseEvent event) {
    }
    public void addSupplyerStage(Stage stage) {
        EffectUtility.makeDraggable(stage, apContent);
    }
    public boolean isNotNull() {
        boolean isNotNull;
        if (tfSupplyerName.getText().trim().isEmpty()
                || tfSupplyerName.getText().trim().isEmpty()
                || tfDate.getValue()==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("ERROR : NULL FOUND");
            alert.setContentText("Please fill all require field");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            
            isNotNull = false;

        } else {
            isNotNull = true;
        }
        return isNotNull;
    }
    private void clrAll() {
        tfSupplyerName.clear();
        tfDate.setValue(LocalDate.now());
    }
}
