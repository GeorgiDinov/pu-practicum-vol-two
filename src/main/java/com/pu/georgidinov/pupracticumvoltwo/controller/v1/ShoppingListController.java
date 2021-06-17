package com.pu.georgidinov.pupracticumvoltwo.controller.v1;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ShoppingListDtoToShoppingList;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ShoppingListToShoppingListDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ShoppingListDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ShoppingListDtoList;
import com.pu.georgidinov.pupracticumvoltwo.domain.ShoppingList;
import com.pu.georgidinov.pupracticumvoltwo.service.ShoppingListService;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ShoppingListDtoList findAllShoppingLists() {
        log.info("ShoppingListController::findAllShoppingLists");
        List<ShoppingList> shoppingLists = this.shoppingListService.findAllShoppingLists();
        List<ShoppingListDto> shoppingListDtos = shoppingLists.stream().map(toShoppingListDto::convert).collect(Collectors.toList());
        return new ShoppingListDtoList(shoppingListDtos);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingListDtoList findAllShoppingListsForUser(@PathVariable Long id) {
        log.info("ShoppingListController::findAllShoppingListsForUser, ID passed = {}", id);
        List<ShoppingList> shoppingLists = this.shoppingListService.findAllShoppingListsByUserId(id);
        List<ShoppingListDto> shoppingListDtos = shoppingLists.stream().map(toShoppingListDto::convert).collect(Collectors.toList());
        return new ShoppingListDtoList(shoppingListDtos);
    }

}