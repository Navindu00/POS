package dao.custom;

import java.util.ArrayList;

import dao.CrudUtil;
import entity.OrderDetail;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public boolean add(OrderDetail orderDetail) throws Exception {
        return CrudUtil.execute("INSERT INTO orderDetail (unit_price, quantity, sub_total, order_id, item_id) VALUES (?,?,?,?,?)",
                orderDetail.getUnitPrice(), orderDetail.getQuantity(),
                orderDetail.getUnitPrice() * orderDetail.getQuantity(), orderDetail.getOrderID(),
                orderDetail.getItemID());
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return false; 
    }

    @Override
    public boolean update(OrderDetail t) throws Exception {
        return false;
    }

    @Override
    public OrderDetail get(Integer id) throws Exception {
        return null;
    }

    @Override
    public ArrayList<OrderDetail> getAll() throws Exception {
        return null;
    }

}
