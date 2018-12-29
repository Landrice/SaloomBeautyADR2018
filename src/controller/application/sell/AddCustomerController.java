package controller.application.sell;

import BLL.CustomerBLL;
import Getway.CustomerGetway;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import media.userNameMedia;
import DAL.Customer;
import com.jfoenix.controls.JFXComboBox;
import controller.application.stock.AddCustomerTypeController;
import controller.application.stock.AddProductController;
import controller.application.stock.AddSupplyerController;
import dataBase.DBConnection;
import dataBase.DBProperties;
import dataBase.SQL;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 * Created by rifat on 8/12/15.
 */
public class AddCustomerController implements Initializable {
    @FXML
    private TextField tfCustomerName;
    @FXML
    private TextArea taCustomerContact;
    @FXML
    private TextArea taCustomerAddress;
    @FXML
    public Button btnSave;
    @FXML
    public Label lblCustomerContent;
    @FXML
    private Button btnClose;
    @FXML
    public Button btnUpdate;
    
    public String customerId;
    
    private String userId;
    
    userNameMedia nameMedia;
    @FXML
    private JFXComboBox<String> tfCustomerType;
    @FXML
    private Button btnAddCustomerType;
    SQL sql = new SQL();

    DBConnection dbCon = new DBConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;
    
    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    public void setNameMedia(userNameMedia nameMedia) {
        userId = nameMedia.getId();
        this.nameMedia = nameMedia;
    }
    
    Customer customer = new Customer();
    CustomerGetway customerGetway = new CustomerGetway();
    CustomerBLL customerBLL = new CustomerBLL();
    
    String customertypeid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // tfCustomerType.getItems().addAll("Premium","Gold","Silver");
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        customertypeid=sql.getIdNo(tfCustomerType.getSelectionModel().getSelectedItem(), customertypeid, "Customerstype", "CustomersType");
        customer.customerName = tfCustomerName.getText().trim();
        customer.customerAddress = taCustomerAddress.getText().trim();
        customer.customerConNo = taCustomerContact.getText().trim();
        customer.userId = userId;
        customer.customertypeId=customertypeid;
        customerBLL.save(customer);
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        customertypeid=sql.getIdNo(tfCustomerType.getSelectionModel().getSelectedItem(), customertypeid, "Customerstype", "CustomersType");
        customer.id = customerId;
        customer.customerName = tfCustomerName.getText().trim();
        customer.customerAddress = taCustomerAddress.getText().trim();
        customer.customerConNo = taCustomerContact.getText().trim();
        customer.customertypeId=customertypeid;
        customerBLL.update(customer);
    }
    
    public void viewDetails(){
        customer.id = customerId;
        customerGetway.selectedView(customer);
        tfCustomerName.setText(customer.customerName);
        taCustomerContact.setText(customer.customerConNo);
        taCustomerAddress.setText(customer.customerAddress);
    }

    @FXML
    private void btnAddCustomerTypeOnAction(ActionEvent event) {
       
        userNameMedia media = new userNameMedia();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/application/stock/AddCustomerType.fxml"));
        try {
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            AddCustomerTypeController supplyerController = fxmlLoader.getController();
            media.setId(userId);
            supplyerController.setMedia(media);
           // supplyerController.lblCaption.setText("Add Supplyer");
            supplyerController.btnUpdate.setVisible(false);
            Stage nStage = new Stage();
            supplyerController.addSupplyerStage(nStage);
            nStage.setScene(scene);
            nStage.initModality(Modality.APPLICATION_MODAL);
            nStage.initStyle(StageStyle.TRANSPARENT);
            nStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void CustomerNametypeOnCliked(MouseEvent event) {
         tfCustomerType.getSelectionModel().clearSelection();
        tfCustomerType.getItems().clear();
        try {
            pst = con.prepareStatement("select * from "+db+".Customerstype");
            rs = pst.executeQuery();
            while (rs.next()) {
                tfCustomerType.getItems().addAll(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void CustomerNametypeOnAction(ActionEvent event) {
    }
}
