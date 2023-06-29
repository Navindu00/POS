package service.custom;

import java.util.ArrayList;

import dto.ItemDTO;
import service.SuperService;

public interface ItemService extends SuperService{
    public boolean addItem(ItemDTO itemDTO) throws Exception;
    public boolean updateItem(ItemDTO itemDTO) throws Exception;
    public boolean deleteItem(Integer id) throws Exception;
    public ItemDTO getItem(Integer id) throws Exception;
    public ArrayList<ItemDTO> getAllItems() throws Exception;
    public ArrayList<ItemDTO> getAllItemsById() throws Exception;
}
