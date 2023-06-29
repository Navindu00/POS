package service.custom.impl;

import java.util.ArrayList;

import dao.DaoFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;
import service.custom.CustomerService;

public class CustomerServiceImpl implements CustomerService {

    CustomerDAO customerDAO = (CustomerDAO)DaoFactory.getInstance().getDao(DaoFactory.DAOType.CUSTOMER);

    @Override
    public boolean addCustomer(CustomerDTO customerDTO) throws Exception {
        return customerDAO.add(new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getEmail(), customerDTO.getPostalCode()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws Exception {
        return customerDAO.update(new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getEmail(), customerDTO.getPostalCode()));
    }

    @Override
    public boolean deleteCustomer(Integer id) throws Exception {
        return customerDAO.delete(id);
    }

    @Override
    public CustomerDTO getCustomer(Integer id) throws Exception {
        Customer customer = customerDAO.get(id);
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getEmail(), customer.getPostalCode());
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws Exception {
        ArrayList<Customer> customerList = customerDAO.getAll();
        ArrayList<CustomerDTO> dtoList = new ArrayList<>();

        for (Customer customer : customerList) {
            dtoList.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getEmail(), customer.getPostalCode()));
        }
        return dtoList;
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomersById() throws Exception {
        ArrayList<Customer> customerList = customerDAO.getAllCustomersByID();
        ArrayList<CustomerDTO> dtoList = new ArrayList<>();

        for (Customer customer : customerList) {
            dtoList.add(new CustomerDTO(customer.getId()));
        }
        return dtoList;
    }

    
}
