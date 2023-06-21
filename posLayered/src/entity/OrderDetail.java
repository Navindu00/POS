package entity;

public class OrderDetail implements SuperEntity{
    private int id;
    private double unitPrice;
    private int quantity;
    private int orderID;
    private int itemID;
    
    public OrderDetail() {
    }

    public OrderDetail(int id, double unitPrice, int quantity, int orderID, int itemID) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.orderID = orderID;
        this.itemID = itemID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public String toString() {
        return "OrderDetail [id=" + id + ", unitPrice=" + unitPrice + ", quantity=" + quantity + ", orderID=" + orderID
                + ", itemID=" + itemID + "]";
    }
    
    
    
}
