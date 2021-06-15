package com.pu.georgidinov.pupracticumvoltwo.controller.v1;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ItemDtoToItemConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ItemToItemDtoConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ItemDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ItemDtoList;
import com.pu.georgidinov.pupracticumvoltwo.domain.Item;
import com.pu.georgidinov.pupracticumvoltwo.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("assistant/api/v1/item")
public class ItemController {

    private final ItemService itemService;
    private final ItemToItemDtoConverter toItemDto;
    private final ItemDtoToItemConverter toItem;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ItemDtoList findAllItems() {
        log.info("ItemController::findAllItems");
        List<ItemDto> itemDtos = this.itemService.findAllItems().stream().map(toItemDto::convert).collect(Collectors.toList());
        return new ItemDtoList(itemDtos);
    }

    @GetMapping("/inlist/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDtoList findAllItemsForShoppingList(@PathVariable Long id) {
        log.info("ItemController::findAllItemsForShoppingList, ID passed = {}", id);
        List<ItemDto> itemDtos = this.itemService.findAllItemsByShoppingListId(id).stream().map(toItemDto::convert).collect(Collectors.toList());
        return new ItemDtoList(itemDtos);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto findItemById(@PathVariable Long id) {
        log.info("ItemController::findAllItemsForShoppingList, ID passed = {}", id);
        return toItemDto.convert(this.itemService.findItemByItemId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto saveItem(@RequestBody ItemDto itemDto) {
        log.info("ItemController::saveItem, DTO passed = {}", itemDto);
        Item item = this.toItem.convert(itemDto);
        Item savedItem = this.itemService.saveItem(item);
        return this.toItemDto.convert(savedItem);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto updateItem(@RequestBody ItemDto itemDto, @PathVariable Long id) {
        log.info("ItemController::updateItem, ID passed = {}", id);
        Item item = this.toItem.convert(itemDto);
        Item updatedItem = this.itemService.updateItem(item, id);
        return this.toItemDto.convert(updatedItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteItem(@PathVariable Long id) {
        log.info("ItemController::deleteItem, ID passed = {}", id);
        this.itemService.deleteItemById(id);
    }

}