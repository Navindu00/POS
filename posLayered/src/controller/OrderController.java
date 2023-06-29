package controller;

import java.util.ArrayList;
import java.util.Date;

import com.jfoenix.controls.JFXButton;

import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import service.ServiceFactory;
import service.custom.CustomerService;
import service.custom.ItemService;
import service.custom.OrderService;
import view.tm.OrderTM;

public class OrderController {
    private CustomerService customerService = (CustomerService) ServiceFactory.getInstance().getService(ServiceFactory.serviceType.CUSTOMER);
    private ItemService itemService = (ItemService) ServiceFactory.getInstance().getService(ServiceFactory.serviceType.ITEM);
    private OrderService orderService = (OrderService) ServiceFactory.getInstance().getService(ServiceFactory.serviceType.ORDER);
    private ArrayList<OrderDetailDTO> detailDtos = new ArrayList<>();
    private ItemDTO tempItem;
    private double total = 0.0;

    @FXML
    private TableColumn<OrderTM, String> ColDescription;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private ComboBox<Integer> cmbCustID;

    @FXML
    private ComboBox<Integer> cmbItemID;

    @FXML
    private TableColumn<OrderTM, Button> colBtnRemove;

    @FXML
    private TableColumn<OrderTM, Integer> colItemID;

    @FXML
    private TableColumn<OrderTM, Integer> colQuantity;

    @FXML
    private TableColumn<OrderTM, Double> colSubTotal;

    @FXML
    private TableColumn<OrderTM, Double> colUnitPrice;

    @FXML
    private Label lblCustAddress;

    @FXML
    private Label lblCustName;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblQoh;

    @FXML
    private Label lblQuantity;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Label lblOrderID;

    @FXML
    private TableView<OrderTM> tblOrder;

    @FXML
    private JFXButton txtPlaceOrder;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtTotal;

    @FXML
    private TextField txtOrderID;

    ObservableList<OrderTM> tmList = FXCollections.observableArrayList();

    public void initialize() {
        loadAllCustomerId();
        loadAllItemId();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        colItemID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColDescription.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colSubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        colBtnRemove.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));

        try {
            Integer itemId = tempItem.getId();
            String description = tempItem.getName();
            Double unitPrice = tempItem.getUnitPrice();
            Integer quantity = Integer.parseInt(txtQuantity.getText());
            Double subTotal = unitPrice * quantity;
            total += subTotal;
            Button btnRemove = new Button("Remove");

            btnRemove.setMaxSize(60, 60);
            btnRemove.setCursor(Cursor.HAND);
            btnRemove.setStyle("-fx-background-color:#e74c3c");
            btnRemove.setTextFill(Color.web("#ecf0f1"));

            OrderDetailDTO detailDTO = new OrderDetailDTO(-1, unitPrice, quantity, itemId, description);
            detailDtos.add(detailDTO);

            txtTotal.setText(Double.toString(total));
            tmList.add(new OrderTM(itemId, description, unitPrice, quantity, subTotal, btnRemove));
            tblOrder.setItems(tmList);
            clearAfterAddToCart();
        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }

    @FXML
    void btnPlaceOnAction(ActionEvent event) {
        try {
            Integer orderId = Integer.parseInt(txtOrderID.getText());
            Date orderdate = new Date();
            Integer custID = cmbCustID.getValue();

            OrderDTO orderDTO = new OrderDTO(orderId, orderdate, custID, total, detailDtos);
            if(orderService.addOrder(orderDTO)){
                new Alert(AlertType.CONFIRMATION, "Order is Saved!").show();
            }else{
                new Alert(AlertType.CONFIRMATION, "ERROR!").show();
            }
            clearAfterPlace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAllCustomerId() {
        try {
            ArrayList<CustomerDTO> customerList = customerService.getAllCustomersById();
            ObservableList<Integer> idList = FXCollections.observableArrayList();

            for (CustomerDTO customerDTO : customerList) {
                idList.add(customerDTO.getId());
            }

            cmbCustID.setItems(idList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAllItemId() {
        try {
            ArrayList<ItemDTO> itemList = itemService.getAllItemsById();
            ObservableList<Integer> idList = FXCollections.observableArrayList();

            for (ItemDTO itemDTO : itemList) {
                idList.add(itemDTO.getId());
            }

            cmbItemID.setItems(idList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cmbCustIdOnAction(ActionEvent event) {
        try {
            Integer custID = cmbCustID.getValue();
            CustomerDTO customerDTO = customerService.getCustomer(custID);

            if (customerDTO != null) {
                lblCustName.setText(customerDTO.getName());
                lblCustAddress.setText(customerDTO.getAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cmbItemIdOnAction(ActionEvent event) {
        try {
            Integer itemId = cmbItemID.getValue();
            ItemDTO itemDTO = itemService.getItem(itemId);

            if(itemDTO != null){
                tempItem = itemDTO;
                lblItemName.setText(itemDTO.getName());
                lblQoh.setText(Integer.toString(itemDTO.getQuantity()));
                lblUnitPrice.setText(Double.toString(itemDTO.getUnitPrice()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearAfterAddToCart(){
        txtQuantity.setText("");
        lblItemName.setText("");
        lblQoh.setText("");
        lblUnitPrice.setText("");
        //cmbItemID.setValue(null);
    }

    private void clearAfterPlace(){
        txtOrderID.setText("");
        lblCustName.setText("");
        lblCustAddress.setText("");
        total = 0.0;
        txtTotal.setText("0.0");
    }


    // ObservableList<OrderTM> tmList = FXCollections.observableArrayList();

    // @FXML
    // void btnAddOnAction(ActionEvent event) {
    // colItemID.setCellValueFactory(new PropertyValueFactory<>("id"));
    // ColDescription.setCellValueFactory(new PropertyValueFactory<>("name"));
    // colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    // colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    // colSubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
    // colBtnRemove.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));

    // try{
    // int itemID = tempItem.getId();
    // String description = tempItem.getName();
    // int quantity = Integer.parseInt(txtQuantity.getText());
    // double unitPrice = tempItem.getUnitPrice();
    // double subTotal = quantity * unitPrice;
    // total += subTotal;

    // OrderDetailDTO odDto = new OrderDetailDTO(-1, unitPrice, quantity, itemID,
    // description);
    // detailDtos.add(odDto);

    // Button btnRemove = new Button("Remove");
    // btnRemove.setMaxSize(60, 60);
    // btnRemove.setCursor(Cursor.HAND);
    // btnRemove.setStyle("-fx-background-color:#e74c3c");
    // btnRemove.setTextFill(Color.web("#ecf0f1"));

    // tmList.add(new OrderTM(itemID, description, unitPrice, quantity, subTotal,
    // btnRemove));
    // tblOrder.setItems(tmList);
    // txtTotal.setText(Double.toString(total));
    // clearAfterAddToCart();
    // }catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    // @FXML
    // void btnPlaceOnAction(ActionEvent event) {
    // try {
    // int orderID = Integer.parseInt(txtOrderID.getText());
    // Date orderdate = new Date();
    // int custId = Integer.parseInt(txtCustID.getText());

    // boolean isOrderAdded = orderService.addOrder(new OrderDTO(orderID, orderdate,
    // custId, total, detailDtos));
    // if(isOrderAdded){
    // new Alert(AlertType.CONFIRMATION, "Order is Saved!").show();
    // }else{
    // new Alert(AlertType.CONFIRMATION, "ERROR!").show();
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    // private void clearAfterAddToCart(){
    // txtItemID.setText("");
    // txtItemData.setText("");
    // txtQuantity.setText("");
    // }
}
