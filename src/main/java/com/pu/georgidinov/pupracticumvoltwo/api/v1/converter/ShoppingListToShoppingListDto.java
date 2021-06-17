package com.pu.georgidinov.pupracticumvoltwo.api.v1.converter;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ItemDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ItemDtoList;
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
                .userId(this.extractUserId(shoppingList))
                .items(this.extractItems(shoppingList));
    }

    private Long extractUserId(ShoppingList shoppingList) {
        Long userId = null;
        if (shoppingList != null && shoppingList.getApplicationUser() != null) {
            userId = shoppingList.getApplicationUser().getId();
        }
        return userId;
    }

    private ItemDtoList extractItems(ShoppingList shoppingList) {
        List<ItemDto> dtos = new ArrayList<>();
        if (shoppingList != null && shoppingList.getItems() != null) {
            shoppingList.getItems().forEach(item -> dtos.add(toItemDto.convert(item)));
        }
        return new ItemDtoList(dtos);
    }
}