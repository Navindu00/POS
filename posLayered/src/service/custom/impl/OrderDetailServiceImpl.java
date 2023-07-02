package service.custom.impl;

import java.util.ArrayList;

import dao.DaoFactory;
import dao.DaoFactory.DAOType;
import dao.custom.OrderDetailDAO;
import dto.OrderDetailDTO;
import entity.OrderDetail;
import service.custom.OrderDetailService;

public class OrderDetailServiceImpl implements OrderDetailService{

    private OrderDetailDAO orderDetailDAO = (OrderDetailDAO)DaoFactory.getInstance().getDao(DAOType.ORDER_DETAIL);

    @Override
    public boolean saveOrderDetails(ArrayList<OrderDetailDTO> orderDetailDTOs) throws Exception {
        for (OrderDetailDTO orderDetailDTO : orderDetailDTOs) {
            boolean isOredrDetailSaved = saveOrderDetails(orderDetailDTO);
            if(!isOredrDetailSaved){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean saveOrderDetails(OrderDetailDTO orderDetailDTO) throws Exception {
        return orderDetailDAO.add(new OrderDetail(orderDetailDTO.getUnitPrice(), orderDetailDTO.getQuantity(), orderDetailDTO.getOrderID(), orderDetailDTO.getItemID()));
    }

    

    
    
}
