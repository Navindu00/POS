package view.tm;

import javafx.scene.control.Button;

public class OrderTM {
    private int id;
    private String name;
    private double unitPrice;
    private int quantity;
    private double subTotal;
    private Button btnRemove;
    
    public OrderTM() {
    }

    public OrderTM(int id, String name, double unitPrice, int quantity, double subTotal, Button btnRemove) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.subTotal = subTotal;
        this.btnRemove = btnRemove;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Button getBtnRemove() {
        return btnRemove;
    }

    public void setBtnRemove(Button btnRemove) {
        this.btnRemove = btnRemove;
    }

    
    
}
