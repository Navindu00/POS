package dto;

public class ItemDTO {
    private int id;
    private String name;
    private double unitPrice;
    private int quantity;
    
    public ItemDTO() {
    }

    public ItemDTO(int id, String name, double unitPrice, int quantity) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "ItemDTO [id=" + id + ", name=" + name + ", unitPrice=" + unitPrice + ", quantity=" + quantity + "]";
    }

    
}
