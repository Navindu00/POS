package dto;

public class OrderDetailDTO {
    private int id;
    private double unitPrice;
    private int quantity;
    private int itemID;
    private String name;
    
    public OrderDetailDTO() {
    }
    
    public OrderDetailDTO(int id, double unitPrice, int quantity, int itemID, String name) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.itemID = itemID;
        this.name = name;
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

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO [id=" + id + ", unitPrice=" + unitPrice + ", quantity=" + quantity + ", itemID=" + itemID
                + ", name=" + name + "]";
    }

    

}
