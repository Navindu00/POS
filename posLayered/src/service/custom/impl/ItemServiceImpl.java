package service.custom.impl;

import java.util.ArrayList;

import dao.DaoFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;
import service.custom.ItemService;

public class ItemServiceImpl implements ItemService{

    ItemDAO itemDao = (ItemDAO)DaoFactory.getInstance().getDao(DaoFactory.DAOType.ITEM);

    @Override
    public boolean addItem(ItemDTO itemDTO) throws Exception {
        return itemDao.add(new Item(itemDTO.getId(), itemDTO.getName(), itemDTO.getUnitPrice(), itemDTO.getQuantity()));
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws Exception {
        return itemDao.update(new Item(itemDTO.getId(), itemDTO.getName(), itemDTO.getUnitPrice(), itemDTO.getQuantity()));
    }

    @Override
    public boolean deleteItem(Integer id) throws Exception {
        return itemDao.delete(id);
    }

    @Override
    public ItemDTO getItem(Integer id) throws Exception {
        Item item = itemDao.get(id);
        return new ItemDTO(item.getId(), item.getName(), item.getUnitPrice(), item.getQuantity());
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws Exception {
        ArrayList<Item> itemList = itemDao.getAll();
        ArrayList<ItemDTO> dtoList = new ArrayList<>();

        for (Item item : itemList) {
            dtoList.add(new ItemDTO(item.getId(), item.getName(), item.getUnitPrice(), item.getQuantity()));
        }

        return dtoList;
    }
    
}
