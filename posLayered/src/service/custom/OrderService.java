package service.custom;


import dto.OrderDTO;
import service.SuperService;

public interface OrderService extends SuperService {
    public boolean addOrder(OrderDTO orderDTO) throws Exception;
    
}
