package com.pu.georgidinov.pupracticumvoltwo.controller.v1;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ItemDtoToItemConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ShoppingListDtoToShoppingList;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ShoppingListToShoppingListDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ItemDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ShoppingListDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ShoppingListDtoList;
import com.pu.georgidinov.pupracticumvoltwo.domain.Item;
import com.pu.georgidinov.pupracticumvoltwo.domain.ShoppingList;
import com.pu.georgidinov.pupracticumvoltwo.service.ShoppingListService;
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
@RequestMapping("assistant/api/v1/shoppinglist")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;
    private final ShoppingListToShoppingListDto toShoppingListDto;
    private final ShoppingListDtoToShoppingList toShoppingList;
    private final ItemDtoToItemConverter toItem;


    @Operation(summary = "This Operation Retrieves All Shopping Lists Stored In DB")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Retrieves All Shopping Lists From DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ShoppingListDtoList findAllShoppingLists() {
        log.info("ShoppingListController::findAllShoppingLists");
        List<ShoppingList> shoppingLists = this.shoppingListService.findAllShoppingLists();
        List<ShoppingListDto> shoppingListDtos = shoppingLists.stream().map(toShoppingListDto::convert).collect(Collectors.toList());
        return new ShoppingListDtoList(shoppingListDtos);
    }

    @Operation(summary = "This Operation Retrieves Single Shopping List Stored In DB By The Shopping List ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Retrieves Single Shopping List From DB",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Returns Error Message If Shopping List With The Passed ID Not Found In DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingListDto findShoppingListById(@PathVariable Long id) {
        log.info("ShoppingListController::findShoppingListById, ID passed = {}", id);
        return this.toShoppingListDto.convert(this.shoppingListService.findShoppingListById(id));
    }

    @Operation(summary = "This Operation Retrieves All Shopping Lists Stored In DB For A Particular User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Retrieves All Shopping Lists For User With The Passed User ID Value",
                            content = {@Content(mediaType = "application/json")})
            })
    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingListDtoList findAllShoppingListsForUser(@PathVariable Long id) {
        log.info("ShoppingListController::findAllShoppingListsForUser, ID passed = {}", id);
        List<ShoppingList> shoppingLists = this.shoppingListService.findAllShoppingListsByUserId(id);
        List<ShoppingListDto> shoppingListDtos = shoppingLists.stream().map(toShoppingListDto::convert).collect(Collectors.toList());
        return new ShoppingListDtoList(shoppingListDtos);
    }

    @Operation(summary = "This Operation Creates Single Shopping List And Stores It In The DB")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201",
                            description = "Creates Single Shopping List And Store It In The DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingListDto saveShoppingList(@RequestBody ShoppingListDto dto) {
        log.info("ShoppingListController::saveShoppingList, DTO passed = {}", dto);
        ShoppingList shoppingList = this.toShoppingList.convert(dto);
        ShoppingList savedShoppingList = this.shoppingListService.saveShoppingList(shoppingList);
        return this.toShoppingListDto.convert(savedShoppingList);
    }

    @Operation(summary = "This Operation Updates Single Shopping List")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Updates Single Shopping List And Store It In The DB",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Returns Error Message If Shopping List With The Passed ID Not Found In DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingListDto updateShoppingList(@RequestBody ShoppingListDto dto, @PathVariable Long id) {
        log.info("ShoppingListController::saveShoppingList, DTO passed = {}, ID passed = {}", dto, id);
        ShoppingList shoppingList = this.toShoppingList.convert(dto);
        ShoppingList updatedList = this.shoppingListService.updateShoppingList(shoppingList, id);
        return this.toShoppingListDto.convert(updatedList);
    }

    @Operation(summary = "This Operation Adds Single Item To The Shopping List")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201",
                            description = "Adds Single Item To The Shopping List And Store It In The DB",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Returns Error Message If Shopping List With The Passed ID Not Found In DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @PostMapping("/item/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingListDto addItemToShoppingList(@RequestBody ItemDto dto, @PathVariable Long id) {
        log.info("ShoppingListController::addItemToShoppingList, DTO passed = {}, ID passed = {}", dto, id);
        Item item = this.toItem.convert(dto);
        ShoppingList shoppingList = this.shoppingListService.addItemToShoppingList(item, id);
        return this.toShoppingListDto.convert(shoppingList);
    }

    @Operation(summary = "This Operation Deletes Single Shopping List From DB")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Deletes Single Shopping List From DB"),
                    @ApiResponse(responseCode = "404",
                            description = "Returns Error Message If Shopping List With The Passed ID Not Found In DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShoppingListById(@PathVariable Long id) {
        log.info("ShoppingListController::deleteShoppingListById, ID passed = {}", id);
        this.shoppingListService.deleteShoppingListById(id);
    }

}