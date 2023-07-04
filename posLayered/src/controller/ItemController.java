package controller;

import java.util.ArrayList;
import java.util.Optional;



import com.jfoenix.controls.JFXButton;

import dto.ItemDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import service.ServiceFactory;
import service.custom.ItemService;
import util.Validator;
import view.tm.ItemTM;

public class ItemController {
    private ItemService itemService;

    @FXML
    private JFXButton btnAddItem;

    @FXML
    private JFXButton btnUpdateItem;

    @FXML
    private TextField txtItemID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TableColumn<ItemTM, Integer> colID;

    @FXML
    private TableColumn<ItemTM, String> colName;

    @FXML
    private TableColumn<ItemTM, Integer> colQuantity;

    @FXML
    private TableColumn<ItemTM, Double> colUnitPrice;

    @FXML
    private TableColumn<ItemTM, Button> colDeleteButton;

    @FXML
    private TableView<ItemTM> tblItems;

    public void initialize() {
        itemService = (ItemService) ServiceFactory.getInstance().getService(ServiceFactory.serviceType.ITEM);

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDeleteButton.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
        getItems();

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        try {
            int id = Integer.parseInt(txtItemID.getText());
            String name = txtName.getText();
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            int quantity = Integer.parseInt(txtQuantity.getText());

            ItemDTO itemDto = new ItemDTO(id, name, unitPrice, quantity);
            boolean isAdded = itemService.addItem(itemDto);

            if (isAdded) {
                new Alert(AlertType.CONFIRMATION, "Item is Saved!").show();
                getItems();
                clear();
            } else {
                new Alert(AlertType.CONFIRMATION, "ERROR!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        try {
            int id = Integer.parseInt(txtItemID.getText());
            String name = txtName.getText();
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            int quantity = Integer.parseInt(txtQuantity.getText());
            boolean isUpdated = itemService.updateItem(new ItemDTO(id, name, unitPrice, quantity));

            if (isUpdated) {
                new Alert(AlertType.CONFIRMATION, "Item is Updated!").show();
                getItems();
                clear();
            } else {
                new Alert(AlertType.CONFIRMATION, "ERROR!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void tblOnMouseClicked(MouseEvent event) {
        getItem();
    }

    private void getItems() {
        try {
            ArrayList<ItemDTO> dtoList = itemService.getAllItems();
            ObservableList<ItemTM> tmList = FXCollections.observableArrayList();

            for (ItemDTO itemDto : dtoList) {
                Button btnDelete = new Button("Delete");
                btnDelete.setMaxSize(60, 60);
                btnDelete.setCursor(Cursor.HAND);
                btnDelete.setStyle("-fx-background-color:#e74c3c");
                btnDelete.setTextFill(Color.web("#ecf0f1"));

                tmList.add(new ItemTM(itemDto.getId(), itemDto.getName(), itemDto.getUnitPrice(), itemDto.getQuantity(),
                        btnDelete));
                btnDelete.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure?", yes, no);
                    Optional<ButtonType> result = alert.showAndWait();

                    try {
                        if (result.orElse(no) == yes) {
                            if (itemService.deleteItem(itemDto.getId())) {
                                new Alert(AlertType.CONFIRMATION, "Item is Deleted!").show();
                                getItems();
                                clear();
                            }
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                });
            }

            tblItems.setItems(tmList);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void getItem() {
        int id = tblItems.getSelectionModel().getSelectedItem().getId();
        try {
            ItemDTO itemDto = itemService.getItem(id);
            txtItemID.setText(Integer.toString(itemDto.getId()));
            txtName.setText(itemDto.getName());
            txtUnitPrice.setText(Double.toString(itemDto.getUnitPrice()));
            txtQuantity.setText(Integer.toString(itemDto.getQuantity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clear() {
        txtItemID.setText("");
        txtName.setText("");
        txtUnitPrice.setText("");
        txtQuantity.setText("");
    }

    @FXML
    void txtIdOnKeyReleased(KeyEvent event) {
        if(Validator.validateTextField(txtItemID, "^[0-9]{1,}$")){
            txtItemID.setStyle("-fx-focus-color:#3498db");
            if(event.getCode() == KeyCode.ENTER){
                txtName.requestFocus();
            }
        }else{
            txtItemID.setStyle("-fx-focus-color:red");
        }
    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {
        if(Validator.validateTextField(txtName, "^[A-z| ]{1,}$|^[A-z| ]{1,} [0-9]{1,}[A-z]{1,5}$")){
            txtName.setStyle("-fx-focus-color:#3498db");
            if(event.getCode() == KeyCode.ENTER){
                txtUnitPrice.requestFocus();
            }
        }else{
            txtName.setStyle("-fx-focus-color:red");
        }
    }

    @FXML
    void txtQuantityOnKeyReleased(KeyEvent event) {
        if(Validator.validateTextField(txtQuantity, "^[0-9]{1,}$")){
            txtQuantity.setStyle("-fx-focus-color:#3498db");
        }else{
            txtQuantity.setStyle("-fx-focus-color:red");
        }
    }

    @FXML
    void txtUnitPriceOnKeyReleased(KeyEvent event) {
        if(Validator.validateTextField(txtUnitPrice, "^[0-9]{1,}$|^[0-9]{1,}.[0-9]{1,}$")){
            txtUnitPrice.setStyle("-fx-focus-color:#3498db");
            if(event.getCode() == KeyCode.ENTER){
                txtQuantity.requestFocus();
            }
        }else{
            txtUnitPrice.setStyle("-fx-focus-color:red");
        }
    }
}
