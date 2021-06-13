package com.pu.georgidinov.pupracticumvoltwo.controller.v1;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ItemDtoToItemConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ItemToItemDtoConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ItemDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ItemDtoList;
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

}