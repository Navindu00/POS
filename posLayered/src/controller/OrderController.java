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
    private ItemService itemService = (ItemService)ServiceFactory.getInstance().getService(ServiceFactory.serviceType.ITEM);
    private OrderService orderService = (OrderService)ServiceFactory.getInstance().getService(ServiceFactory.serviceType.ORDER);
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

        try{
            int itemId = tempItem.getId();
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

    public OrderDTO getOrder(){
        int orderId = Integer.parseInt(txtOrderID.getText());
        Date orderDate = new Date();
        int custId = cmbCustID.getValue();
        return new OrderDTO(orderId, orderDate, custId, total);
    }

    public ArrayList<OrderDetailDTO> getOrderDetails(){
        int orderId = Integer.parseInt(txtOrderID.getText());

        ArrayList<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();
        for(int i=0; i<tblOrder.getItems().size(); i++){
            OrderTM orderTM = tmList.get(i);
            orderDetailDTOs.add(new OrderDetailDTO(orderTM.getUnitPrice(), orderTM.getQuantity(), orderTM.getId(), orderId));
        }
        return orderDetailDTOs;
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

}
