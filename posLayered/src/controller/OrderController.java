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
    private JFXButton btnCustSearch;

    @FXML
    private JFXButton btnItemSearch;

     @FXML
    private TableView<OrderTM> tblOrder;
    
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
    private Label lblTotal;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private TextField txtCustData;

    @FXML
    private TextField txtCustID;

    @FXML
    private TextField txtItemData;

    @FXML
    private TextField txtItemID;

    @FXML
    private TextField txtOrderID;

    @FXML
    private JFXButton txtPlaceOrder;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtTotal;

    @FXML
    void btnCustSearchOnAction(ActionEvent event) {

        try {
            CustomerDTO customerDto = customerService.getCustomer(Integer.parseInt(txtCustID.getText()));
            if (customerDto != null) {
                txtCustData.setText("Customer : " +customerDto.getName());
            } else {
                new Alert(AlertType.CONFIRMATION, "Invalid ID!").show();
            }
        } catch (NullPointerException | NumberFormatException e) {
            new Alert(AlertType.ERROR, "Invalid Customer ID!").show();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnItemSearchOnAction(ActionEvent event) {
    
        try {
            ItemDTO itemDto = itemService.getItem(Integer.parseInt(txtItemID.getText()));
            if(itemDto != null){
                txtItemData.setText("Description : "+ itemDto.getName()+"  |  Unit Price (LKR) : "+ itemDto.getUnitPrice()+ "  |  QOH : "+ itemDto.getQuantity());
                tempItem = itemDto;
            }
        } catch (NullPointerException | NumberFormatException e) {
            new Alert(AlertType.ERROR, "Invalid Item ID!").show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    ObservableList<OrderTM> tmList = FXCollections.observableArrayList();

    @FXML
    void btnAddOnAction(ActionEvent event) {
        colItemID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColDescription.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colSubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        colBtnRemove.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));

        try{
            int itemID = tempItem.getId();
            String description = tempItem.getName();
            int quantity = Integer.parseInt(txtQuantity.getText());
            double unitPrice = tempItem.getUnitPrice();
            double subTotal = quantity * unitPrice;
            total += subTotal;
            
            OrderDetailDTO odDto = new OrderDetailDTO(-1, unitPrice, quantity, itemID, description);
            detailDtos.add(odDto);

            Button btnRemove = new Button("Remove");
            btnRemove.setMaxSize(60, 60);
            btnRemove.setCursor(Cursor.HAND);
            btnRemove.setStyle("-fx-background-color:#e74c3c");
            btnRemove.setTextFill(Color.web("#ecf0f1"));

            tmList.add(new OrderTM(itemID, description, unitPrice, quantity, subTotal, btnRemove));
            tblOrder.setItems(tmList);
            txtTotal.setText(Double.toString(total));
            clearAfterAddToCart();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnPlaceOnAction(ActionEvent event) {
        try {
            int orderID = Integer.parseInt(txtOrderID.getText());
            Date orderdate = new Date();
            int custId = Integer.parseInt(txtCustID.getText());

            boolean isOrderAdded = orderService.addOrder(new OrderDTO(orderID, orderdate, custId, total, detailDtos));
            if(isOrderAdded){
                new Alert(AlertType.CONFIRMATION, "Order is Saved!").show();
            }else{
                new Alert(AlertType.CONFIRMATION, "ERROR!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearAfterAddToCart(){
        txtItemID.setText("");
        txtItemData.setText("");
        txtQuantity.setText("");
    }
}
