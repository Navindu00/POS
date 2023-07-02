package dao.custom;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import entity.Item;

public class ItemDAOImpl implements ItemDAO{

    @Override
    public boolean add(Item item) throws Exception {
        return CrudUtil.execute("INSERT INTO item VALUES (?,?,?,?)", item.getId(),item.getName(),item.getUnitPrice(), item.getQuantity());
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.execute("DELETE FROM item WHERE item_id = ?", id);
    }

    @Override
    public boolean update(Item item) throws Exception {
        return CrudUtil.execute("UPDATE item SET name=?, unit_price=?, quantity=? WHERE item_id=?", item.getName(),item.getUnitPrice(), item.getQuantity(),item.getId());
    }

    @Override
    public Item get(Integer id) throws Exception {
        ResultSet itemResult = CrudUtil.execute("SELECT * FROM item WHERE item_id = ?",id);

        while(itemResult.next()){
            return new Item(itemResult.getInt(1), itemResult.getString(2), itemResult.getDouble(3), itemResult.getInt(4));
        }
        return null;
    }

    @Override
    public ArrayList<Item> getAll() throws Exception {
        ResultSet itemSet = CrudUtil.execute("SELECT * FROM item");
        ArrayList<Item> itemList = new ArrayList<>();

        while(itemSet.next()){
            itemList.add(new Item(itemSet.getInt(1), itemSet.getString(2), itemSet.getDouble(3), itemSet.getInt(4)));
        }
        return itemList;
    }

    @Override
    public ArrayList<Item> getAllItemsByID() throws Exception {
        ResultSet itemIdSet = CrudUtil.execute("SELECT item_id FROM item");
        ArrayList<Item> itemList = new ArrayList<>();

        while(itemIdSet.next()){
            itemList.add(new Item(itemIdSet.getInt(1)));
        }
        return itemList;
    }
    
}
