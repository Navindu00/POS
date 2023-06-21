package dao;

import dao.custom.CustomerDaoImpl;
import dao.custom.ItemDAOImpl;
import dao.custom.OrderDAOImpl;
import dao.custom.OrderDetailDAOImpl;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return (daoFactory == null) ? (daoFactory = new DaoFactory()) : daoFactory;
    }

    public enum DAOType{
        CUSTOMER,ITEM,ORDER, ORDER_DETAIL
    }

    public SuperDAO getDao(DAOType type){
        switch (type) {
            case CUSTOMER:
                return new CustomerDaoImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            default:
                return null;
        }
    }
}
