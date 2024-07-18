package com.java.inventory.controller;

import com.java.inventory.dto.InventoryDTO;
import com.java.inventory.service.InventoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/")
@Log4j2
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/items")
    public List<InventoryDTO> getItems() {
        return inventoryService.getAllItems();
    }

    @GetMapping("/items/{itemId}")
    public InventoryDTO getItemsById(@PathVariable Integer itemId) {
        return inventoryService.getItemById(itemId);
    }

    @PostMapping("/items")
    public InventoryDTO saveItem(@RequestBody InventoryDTO inventoryDTO) {
        log.info("Save mapping");
        return inventoryService.saveItem(inventoryDTO);
    }

    @PutMapping("/items")
    public InventoryDTO updateItem(@RequestBody InventoryDTO inventoryDTO) {
        log.info("Update mapping");
        return inventoryService.updateItem(inventoryDTO);
    }

    @DeleteMapping("/items/{itemId}")
    public String deleteItem(@PathVariable Integer itemId) {
        return inventoryService.deleteItem(itemId);
    }
}
