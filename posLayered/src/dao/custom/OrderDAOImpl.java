package dao.custom;

import java.util.ArrayList;

import dao.CrudUtil;
import entity.Order;

public class OrderDAOImpl implements OrderDAO{

    @Override
    public boolean add(Order order) throws Exception {
        return CrudUtil.execute("INSERT INTO orders VALUES (?,?,?,?)", order.getOrderID(),order.getOrderDate(),order.getTotal(), order.getCustomerID());
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return false;
    }

    @Override
    public boolean update(Order t) throws Exception {
        return false;
    }

    @Override
    public Order get(Integer id) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Order> getAll() throws Exception {
        return null;
    }
    
}
