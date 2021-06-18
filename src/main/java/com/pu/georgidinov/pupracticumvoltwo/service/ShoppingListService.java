package com.pu.georgidinov.pupracticumvoltwo.service;

import com.pu.georgidinov.pupracticumvoltwo.domain.Item;
import com.pu.georgidinov.pupracticumvoltwo.domain.ShoppingList;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceNotFoundException;
import com.pu.georgidinov.pupracticumvoltwo.repository.ShoppingListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    public List<ShoppingList> findAllShoppingLists() {
        log.info("ShoppingListService::findAllShoppingLists");
        List<ShoppingList> lists = new ArrayList<>();
        this.shoppingListRepository.findAll().forEach(lists::add);
        return lists;
    }

    public ShoppingList findShoppingListById(Long id) {
        log.info("ShoppingListService::findShoppingListById, ID passed = {}", id);
        return this.shoppingListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping List with ID = '" + id + "' NOT FOUND"));
    }

    public List<ShoppingList> findAllShoppingListsByUserId(Long id) {
        log.info("ShoppingListService::findAllShoppingListsByUserId, ID passed = {}", id);
        return new ArrayList<>(this.shoppingListRepository.findAllByApplicationUserId(id));
    }

    public ShoppingList saveShoppingList(ShoppingList shoppingList) {
        log.info("ShoppingListService::saveShoppingList, ShoppingList passed = {}", shoppingList);
        return this.shoppingListRepository.save(shoppingList);
    }

    public ShoppingList updateShoppingList(ShoppingList shoppingList, Long id) {
        log.info("ShoppingListService::updateShoppingList, ShoppingList passed = {}, ID = {}", shoppingList, id);
        return this.shoppingListRepository.findById(id)
                .map(list -> {
                    list
                            .id(id)
                            .title(shoppingList.getTitle())
                            .applicationUser(shoppingList.getApplicationUser())
                            .items(shoppingList.getItems());
                    return this.shoppingListRepository.save(list);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Shopping List with ID = '" + id + "' NOT FOUND"));
    }

    public ShoppingList addItemToShoppingList(Item item, Long shoppingListId) {
        log.info("ShoppingListService::updateShoppingList, Item passed = {}, ID = {}", item, shoppingListId);
        return this.shoppingListRepository.findById(shoppingListId)
                .map(list -> {
                    list.addItem(item);
                    return this.shoppingListRepository.save(list);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Shopping List with ID = '" + shoppingListId + "' NOT FOUND"));
    }

    public void deleteShoppingListById(Long id) {
        log.info("ShoppingListService::deleteShoppingListById, ID passed = {}", id);
        if (!this.isShoppingListWithIdExists(id)) {
            throw new ResourceNotFoundException("Shopping List with ID = '" + id + "' NOT FOUND");
        }
        this.shoppingListRepository.deleteById(id);
    }

    private boolean isShoppingListWithIdExists(Long id) {
        return this.shoppingListRepository.findById(id).isPresent();
    }

}