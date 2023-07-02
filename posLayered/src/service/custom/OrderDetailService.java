package service.custom;

import java.util.ArrayList;

import dto.OrderDetailDTO;
import service.SuperService;

public interface OrderDetailService extends SuperService{
    public boolean saveOrderDetails(ArrayList<OrderDetailDTO> orderDetailDTO) throws Exception;
    public boolean saveOrderDetails(OrderDetailDTO orderDetailDTO) throws Exception;
}
