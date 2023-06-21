package controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnItem;

    @FXML
    private JFXButton btnOrder;

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/CustomerView.fxml"))));
        stage.setTitle("Manage Customer");
        stage.show();
    }

    @FXML
    void btnItemOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/ItemView.fxml"))));
        stage.setTitle("Manage Item");
        stage.show();
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/OrderView.fxml"))));
        stage.setTitle("Manage Order");
        stage.show();
    }
}
