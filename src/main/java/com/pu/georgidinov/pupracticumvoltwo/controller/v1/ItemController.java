package com.pu.georgidinov.pupracticumvoltwo.controller.v1;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ItemDtoToItemConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ItemToItemDtoConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ItemDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ItemDtoList;
import com.pu.georgidinov.pupracticumvoltwo.domain.Item;
import com.pu.georgidinov.pupracticumvoltwo.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "This Operation Retrieves All Items Stored In DB")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Retrieves All Items From DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ItemDtoList findAllItems() {
        log.info("ItemController::findAllItems");
        List<ItemDto> itemDtos = this.itemService.findAllItems().stream().map(toItemDto::convert).collect(Collectors.toList());
        return new ItemDtoList(itemDtos);
    }

    @Operation(summary = "This Operation Retrieves All Items For A Particular Shopping List Stored In DB")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Retrieves All Items For A Particular Shopping List From DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @GetMapping("/inlist/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDtoList findAllItemsForShoppingList(@PathVariable Long id) {
        log.info("ItemController::findAllItemsForShoppingList, ID passed = {}", id);
        List<ItemDto> itemDtos = this.itemService.findAllItemsByShoppingListId(id).stream().map(toItemDto::convert).collect(Collectors.toList());
        return new ItemDtoList(itemDtos);
    }

    @Operation(summary = "This Operation Retrieves Single Item Stored In DB By The Item ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Retrieves Single Item By It's ID From DB",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Returns Error Message If Item Not Found In DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto findItemById(@PathVariable Long id) {
        log.info("ItemController::findAllItemsForShoppingList, ID passed = {}", id);
        return toItemDto.convert(this.itemService.findItemByItemId(id));
    }

    @Operation(summary = "This Operation Creates Single Item And Stores It In The DB")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201",
                            description = "Creates Single Item And Store It In The DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto saveItem(@RequestBody ItemDto itemDto) {
        log.info("ItemController::saveItem, DTO passed = {}", itemDto);
        Item item = this.toItem.convert(itemDto);
        Item savedItem = this.itemService.saveItem(item);
        return this.toItemDto.convert(savedItem);
    }

    @Operation(summary = "This Operation Updates Single Item And Stores It In The DB")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Updates Single Item And Store It In The DB",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Returns Error Message If Item Not Found In DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto updateItem(@RequestBody ItemDto itemDto, @PathVariable Long id) {
        log.info("ItemController::updateItem, ID passed = {}", id);
        Item item = this.toItem.convert(itemDto);
        Item updatedItem = this.itemService.updateItem(item, id);
        return this.toItemDto.convert(updatedItem);
    }

    @Operation(summary = "This Operation Deletes Single Item From DB")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Deletes Single Item From DB",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Returns Error Message If Item Not Found In DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteItem(@PathVariable Long id) {
        log.info("ItemController::deleteItem, ID passed = {}", id);
        this.itemService.deleteItemById(id);
    }

}