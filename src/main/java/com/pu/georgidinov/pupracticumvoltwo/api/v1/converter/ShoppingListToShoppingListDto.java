package com.pu.georgidinov.pupracticumvoltwo.api.v1.converter;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ItemDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ShoppingListDto;
import com.pu.georgidinov.pupracticumvoltwo.domain.ShoppingList;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ShoppingListToShoppingListDto implements Converter<ShoppingList, ShoppingListDto> {

    private final ItemToItemDtoConverter toItemDto;

    @Synchronized
    @Override
    public ShoppingListDto convert(ShoppingList shoppingList) {
        return new ShoppingListDto()
                .id(shoppingList.getId())
                .title(shoppingList.getTitle())
                .user(this.extractUser(shoppingList))
                .items(this.extractItems(shoppingList));
    }

    private String extractUser(ShoppingList shoppingList) {
        String user = "";
        if (shoppingList != null && shoppingList.getApplicationUser() != null) {
            user = shoppingList.getApplicationUser().getApplicationUserCredentials().getEmail();
        }
        return user;
    }

    private List<ItemDto> extractItems(ShoppingList shoppingList) {
        List<ItemDto> dtos = new ArrayList<>();
        if (shoppingList != null && shoppingList.getItems() != null) {
            shoppingList.getItems().forEach(item -> dtos.add(toItemDto.convert(item)));
        }
        return dtos;
    }
}