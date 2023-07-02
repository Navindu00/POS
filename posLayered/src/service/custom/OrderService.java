package service.custom;


import java.util.ArrayList;

import dto.OrderDTO;
import dto.OrderDetailDTO;
import service.SuperService;

public interface OrderService extends SuperService {
    public boolean addOrder(OrderDTO orderDTO, ArrayList<OrderDetailDTO> detailDtos) throws Exception;
    
}
