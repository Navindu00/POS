package service.custom;

import java.util.ArrayList;

import dto.CustomerDTO;
import service.SuperService;

public interface CustomerService extends SuperService {
    public boolean addCustomer(CustomerDTO customerDTO) throws Exception;
    public boolean updateCustomer(CustomerDTO customerDTO) throws Exception;
    public boolean deleteCustomer(Integer id) throws Exception;
    public CustomerDTO getCustomer(Integer id) throws Exception;
    public ArrayList<CustomerDTO> getAllCustomers() throws Exception;
}
