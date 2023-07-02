package dto;

public class OrderDetailDTO {
    private double unitPrice;
    private int quantity;
    private int itemID;
    private int orderID;
    
    public OrderDetailDTO() {
    }
    

    public OrderDetailDTO(double unitPrice, int quantity, int itemID, int orderID) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.itemID = itemID;
        this.orderID = orderID;
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

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }


    public int getOrderID() {
        return orderID;
    }


    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }


    @Override
    public String toString() {
        return "OrderDetailDTO [unitPrice=" + unitPrice + ", quantity=" + quantity + ", itemID=" + itemID + ", orderID="
                + orderID + "]";
    }


    

    

    

}
