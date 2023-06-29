package dao.custom;

import java.util.ArrayList;

import dao.CrudDAO;
import entity.Customer;

public interface CustomerDAO extends CrudDAO<Customer, Integer> {
    public ArrayList<Customer> getAllCustomersByID() throws Exception;
}
