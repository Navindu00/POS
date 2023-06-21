package dao.custom;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import entity.Customer;

public class CustomerDaoImpl implements CustomerDAO {

    @Override
    public boolean add(Customer customer) throws Exception {
        return CrudUtil.execute("INSERT INTO customer VALUES (?,?,?,?,?)",
                customer.getId(), customer.getName(), customer.getAddress(), customer.getEmail(),
                customer.getPostalCode());
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.execute("DELETE FROM customer where customer_id = ?", id);
    }

    @Override
    public boolean update(Customer customer) throws Exception {
        return CrudUtil.execute("UPDATE customer SET name = ?, address = ?, email = ?, postal_code = ? WHERE customer_id = ?",customer.getName(), customer.getAddress(), customer.getEmail(), customer.getPostalCode(), customer.getId());
    }

    @Override
    public Customer get(Integer id) throws Exception {
        ResultSet customerSet = CrudUtil.execute("SELECT * FROM customer WHERE customer_id = ?", id);
        
        while(customerSet.next()){
            return new Customer(customerSet.getInt(1), customerSet.getString(2), customerSet.getString(3), customerSet.getString(4), customerSet.getInt(5));
        }
        return null;
        
    }

    @Override
    public ArrayList<Customer> getAll() throws Exception {
        ResultSet customerSet = CrudUtil.execute("SELECT * FROM customer");

        ArrayList<Customer> customerList = new ArrayList<>();
        while(customerSet.next()){
            customerList.add(new Customer(customerSet.getInt(1), customerSet.getString(2), customerSet.getString(3), customerSet.getString(4), customerSet.getInt(5)));
        }
        return customerList;
    }

}
