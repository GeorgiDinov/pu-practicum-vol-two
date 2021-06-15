package com.pu.georgidinov.pupracticumvoltwo.service;

import com.pu.georgidinov.pupracticumvoltwo.domain.Item;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceNotFoundException;
import com.pu.georgidinov.pupracticumvoltwo.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> findAllItems() {
        log.info("ItemService::findAllItems");
        List<Item> items = new ArrayList<>();
        this.itemRepository.findAll().forEach(items::add);
        return items;
    }

    public List<Item> findAllItemsByShoppingListId(Long id) {
        log.info("ItemService::findAllItemsByShoppingListId, ID passed = {}", id);
        return new ArrayList<>(this.itemRepository.findItemsByShoppingListId(id));
    }

    public Item findItemByItemId(Long id) {
        log.info("ItemService::findItemByItemId, ID passed = {}", id);
        return this.itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item with id = '" + id + "' NOT FOUND"));
    }

    public Item saveItem(Item item) {
        log.info("ItemService::saveItem");
        return this.itemRepository.save(item);
    }

    public Item updateItem(Item item, Long id) {
        log.info("ItemService::updateItem");
        return this.itemRepository.findById(id)
                .map(foundItem -> {
                    foundItem.id(id)
                            .name(item.getName())
                            .quantity(item.getQuantity())
                            .shoppingList(item.getShoppingList())
                            .unitOfMeasure(item.getUnitOfMeasure());
                    return this.itemRepository.save(foundItem);
                }).orElseThrow(() -> new ResourceNotFoundException("Item with id = '" + id + "' NOT FOUND"));
    }

    public void deleteItemById(Long id) {
        log.info("ItemService::deleteItemById, ID passed = {}", id);
        if (!isItemWithIdExists(id)) {
            throw new ResourceNotFoundException("Item with id = '" + id + "' NOT FOUND");
        }
        this.itemRepository.deleteById(id);
    }

    //== private methods ==
    private boolean isItemWithIdExists(Long id) {
        return this.itemRepository.findById(id).isPresent();
    }

}
