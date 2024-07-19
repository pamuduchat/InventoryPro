package com.java.inventory.service;

import com.java.inventory.dto.InventoryDTO;
import com.java.inventory.model.Inventory;
import com.java.inventory.repository.InventoryRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Log4j2
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<InventoryDTO> getAllItems() {
        List<Inventory> itemList = inventoryRepository.findAll();
        return modelMapper.map(itemList, new TypeToken<List<InventoryDTO>>(){}.getType());
    }

    public InventoryDTO saveItem(InventoryDTO inventoryDTO) {
        Inventory saved = inventoryRepository.save(modelMapper.map(inventoryDTO, Inventory.class));
        return modelMapper.map(saved, InventoryDTO.class);
    }

    public InventoryDTO updateItem(InventoryDTO inventoryDTO){
        Inventory saved = inventoryRepository.save(modelMapper.map(inventoryDTO, Inventory.class));
        return modelMapper.map(saved, InventoryDTO.class);
    }

    public String deleteItem(Integer itemId){
        inventoryRepository.deleteById(itemId);
        return "Item deleted successfully";
    }

    public InventoryDTO getItemByItemId(Integer itemId){
        Inventory inventory = inventoryRepository.getItemByItemId(itemId);
        return modelMapper.map(inventory, InventoryDTO.class);
    }

}
