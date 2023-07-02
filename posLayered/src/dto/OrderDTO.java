package dto;

import java.util.ArrayList;
import java.util.Date;

public class OrderDTO {
    private int orderID;
    private Date orderDate;
    private int customerID;
    private double total;

    private ArrayList<OrderDetailDTO> detailDtos;

    public OrderDTO() {
    }

    public OrderDTO(int orderID, Date orderDate, int customerID, double total) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.customerID = customerID;
        this.total = total;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    } 
    
}
