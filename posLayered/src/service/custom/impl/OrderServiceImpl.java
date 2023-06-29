package service.custom.impl;



import java.sql.Connection;

import dao.DaoFactory;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import db.DbConnection;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import service.custom.OrderService;

public class OrderServiceImpl implements OrderService{

    OrderDAO orderDao = (OrderDAO)DaoFactory.getInstance().getDao(DaoFactory.DAOType.ORDER);
    OrderDetailDAO orderDetailDao = (OrderDetailDAO)DaoFactory.getInstance().getDao(DaoFactory.DAOType.ORDER_DETAIL);
    ItemDAO itemDAO = (ItemDAO)DaoFactory.getInstance().getDao(DaoFactory.DAOType.ITEM);

    @Override
    public boolean addOrder(OrderDTO orderDTO) throws Exception {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            if(orderDao.add(new Order(orderDTO.getOrderID(), orderDTO.getOrderDate(), orderDTO.getCustomerID(), orderDTO.getTotal()))){
            boolean isorderDetailsSaved = true;

            for (OrderDetailDTO detaildto : orderDTO.getDetailDtos()) {
                if(!(orderDetailDao.add(new OrderDetail(-1, detaildto.getUnitPrice(), detaildto.getQuantity(), orderDTO.getOrderID(), detaildto.getItemID())))){
                    isorderDetailsSaved = false;
                }
            }

            if(isorderDetailsSaved){
                boolean isItemUpdated = true;

                for (OrderDetailDTO detaildto : orderDTO.getDetailDtos()) {
                    Item item =  itemDAO.get(detaildto.getItemID());
                    item.setQuantity(item.getQuantity() - detaildto.getQuantity());
                    if(!(itemDAO.update(item))){
                        isItemUpdated = false;
                    }
                }

                if(isItemUpdated){
                    connection.commit();
                    return true;
                }else{
                    connection.rollback();
                    return false;
                }
            }else{
                connection.rollback();
                return false;
            }
            
        }else{
            connection.rollback();
            return false;
        }
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally{
            connection.setAutoCommit(true);
        }
    }

}
