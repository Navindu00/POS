package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import dto.CustomerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import service.ServiceFactory;
import service.custom.CustomerService;
import util.Validator;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tm.CustomerTM;

public class CustomerController {
    private CustomerService customerService;
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCustID;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPostalCode;

    @FXML
    private TableView<CustomerTM> tblCustomers;

    @FXML
    private TableColumn<CustomerTM, String> colAddress;

    @FXML
    private TableColumn<CustomerTM, String> colEmail;

    @FXML
    private TableColumn<CustomerTM, Integer> colID;

    @FXML
    private TableColumn<CustomerTM, String> colName;

    @FXML
    private TableColumn<CustomerTM, Button> colDeleteButton;

    @FXML
    private TableColumn<CustomerTM, Integer> colPostalCode;

    public void initialize() throws ClassNotFoundException, SQLException {
        customerService = (CustomerService) ServiceFactory.getInstance()
                .getService(ServiceFactory.serviceType.CUSTOMER);

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colDeleteButton.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
        getCustomers();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtCustID.getText());
            String name = txtName.getText();
            String address = txtAddress.getText();
            String email = txtEmail.getText();
            int postalCode = Integer.parseInt(txtPostalCode.getText());

            CustomerDTO customerDto = new CustomerDTO(id, name, address, email, postalCode);

            boolean isAdded = customerService.addCustomer(customerDto);

            if (isAdded) {
                new Alert(AlertType.CONFIRMATION, "Customer is Saved!").show();
                getCustomers();
            } else {
                new Alert(AlertType.ERROR, "Customer is not Saved!").show();
            }
        } catch (ClassNotFoundException c) {
            new Alert(AlertType.ERROR, "Class is not found " + c).show();
        } catch (SQLException s) {
            new Alert(AlertType.ERROR, "SQL Exception " + s).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        clearForm();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtCustID.getText());
            String name = txtName.getText();
            String address = txtAddress.getText();
            String email = txtEmail.getText();
            int postalCode = Integer.parseInt(txtPostalCode.getText());
            boolean isUpdated = customerService.updateCustomer(new CustomerDTO(id, name, address, email, postalCode));
            if (isUpdated) {
                new Alert(AlertType.CONFIRMATION, "Customer is Updated!").show();
                getCustomers();
            } else {
                new Alert(AlertType.ERROR, "Customer is not Updated!").show();
            }

            getCustomers();
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void tblOnMouseClicked(MouseEvent event) throws ClassNotFoundException, SQLException {
        getCustomer();
    }

    private void getCustomers() throws ClassNotFoundException, SQLException {
        try {
            ArrayList<CustomerDTO> dtoList = customerService.getAllCustomers();
            ObservableList<CustomerTM> tmList = FXCollections.observableArrayList();

            for (CustomerDTO customerDto : dtoList) {
                Button btnDelete = new Button("Delete");
                btnDelete.setMaxSize(60, 60);
                btnDelete.setCursor(Cursor.HAND);
                btnDelete.setStyle("-fx-background-color:#e74c3c");
                btnDelete.setTextFill(Color.web("#ecf0f1"));

                CustomerTM customerTM = new CustomerTM(customerDto.getId(), customerDto.getName(),
                        customerDto.getAddress(),
                        customerDto.getEmail(), customerDto.getPostalCode(), btnDelete);
                tmList.add(customerTM);

                btnDelete.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure?", yes, no);
                    Optional<ButtonType> result = alert.showAndWait();

                    try {
                        if (result.orElse(no) == yes) {
                            if (customerService.deleteCustomer(customerDto.getId())) {
                                new Alert(AlertType.CONFIRMATION, "Customer is Deleted!").show();
                                getCustomers();
                                clearForm();
                            }

                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                });

            }
            tblCustomers.setItems(tmList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        txtCustID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtPostalCode.setText("");
    }

    public void getCustomer() {
        int id = tblCustomers.getSelectionModel().getSelectedItem().getId();
        try {
            CustomerDTO customerDTO = customerService.getCustomer(id);
            txtCustID.setText(Integer.toString(customerDTO.getId()));
            txtName.setText(customerDTO.getName());
            txtAddress.setText(customerDTO.getAddress());
            txtEmail.setText(customerDTO.getEmail());
            txtPostalCode.setText(Integer.toString(customerDTO.getPostalCode()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void txtAddressOnKeyReleased(KeyEvent event) {
        if(Validator.validateTextField(txtAddress, "^[A-z| ]{1,}$")){
            txtAddress.setStyle("-fx-focus-color:#3498db");
            if(event.getCode() == KeyCode.ENTER){
                txtEmail.requestFocus();
            }
        }else{
            txtAddress.setStyle("-fx-focus-color:red");
        }
    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        if(Validator.validateTextField(txtEmail, "^[a-z|0-9]{1,}@[a-z]{1,}.com")){
            txtEmail.setStyle("-fx-focus-color:#3498db");
            if(event.getCode() == KeyCode.ENTER){
                txtPostalCode.requestFocus();
            }
        }else{
            txtEmail.setStyle("-fx-focus-color:red");
        }
    }

    @FXML
    void txtIdOnKeyReleased(KeyEvent event) { 
        if(Validator.validateTextField(txtCustID, "^[0-9]{1,}$")){
            txtCustID.setStyle("-fx-focus-color:#3498db");
            if(event.getCode() == KeyCode.ENTER){
                txtName.requestFocus();
            }
        }else{
            txtCustID.setStyle("-fx-focus-color:red");
        }
    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {
        if(Validator.validateTextField(txtName, "^[A-z| ]{1,}$")){
            txtName.setStyle("-fx-focus-color:#3498db");
            if(event.getCode() == KeyCode.ENTER){
                txtAddress.requestFocus();
            }
        }else{
            txtName.setStyle("-fx-focus-color:red");
        }
    }

    @FXML
    void txtPostalOnKeyReleased(KeyEvent event) {
        if(Validator.validateTextField(txtPostalCode, "^[0-9]{1,}$")){
            txtPostalCode.setStyle("-fx-focus-color:#3498db");
        }else{
            txtPostalCode.setStyle("-fx-focus-color:red");
        }
    }

}
