package service.custom.impl;

import java.sql.Connection;
import java.util.ArrayList;

import dao.DaoFactory;
import dao.custom.OrderDAO;
import db.DbConnection;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Order;
import service.ServiceFactory;
import service.ServiceFactory.serviceType;
import service.custom.ItemService;
import service.custom.OrderDetailService;
import service.custom.OrderService;

public class OrderServiceImpl implements OrderService {

    OrderDAO orderDao = (OrderDAO) DaoFactory.getInstance().getDao(DaoFactory.DAOType.ORDER);
    OrderDetailService orderDetailService = (OrderDetailService) ServiceFactory.getInstance().getService(serviceType.ORDER_DETAIL);
    ItemService itemService = (ItemService) ServiceFactory.getInstance().getService(serviceType.ITEM);

    @Override
    public boolean addOrder(OrderDTO orderDTO, ArrayList<OrderDetailDTO> detailDTOs) throws Exception {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        
        try {
            connection.setAutoCommit(false);
            boolean isOrderSaved = orderDao.add(new Order(orderDTO.getOrderID(), orderDTO.getOrderDate(),orderDTO.getCustomerID(), orderDTO.getTotal()));
            boolean isOrderDetailsSaved = orderDetailService.saveOrderDetails(detailDTOs);
            boolean isItemUpdated = itemService.updateItemWhenOrder(detailDTOs);

            if (isOrderSaved && isOrderDetailsSaved && isItemUpdated) {
                connection.commit();
                return true;
            } else {
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
