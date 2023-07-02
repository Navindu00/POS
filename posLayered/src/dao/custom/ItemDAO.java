package dao.custom;

import java.util.ArrayList;

import dao.CrudDAO;
import entity.Item;

public interface ItemDAO extends CrudDAO<Item, Integer> {
    public ArrayList<Item> getAllItemsByID() throws Exception;
    public boolean updateItemWhenOrder(Item item) throws Exception;
}
