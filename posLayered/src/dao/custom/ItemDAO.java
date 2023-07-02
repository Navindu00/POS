package dao.custom;

import dao.CrudDAO;
import entity.Item;

public interface ItemDAO extends CrudDAO<Item, Integer> {
    public boolean updateItemWhenOrder(Item item) throws Exception;
}
