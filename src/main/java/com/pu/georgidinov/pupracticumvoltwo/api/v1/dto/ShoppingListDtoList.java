package com.pu.georgidinov.pupracticumvoltwo.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListDtoList {

    @JsonProperty("shopping_lists")
    private List<ShoppingListDto> shoppingLists = new ArrayList<>();

    public ShoppingListDtoList(List<ShoppingListDto> shoppingLists) {
        this.setShoppingLists(shoppingLists);
    }

    public List<ShoppingListDto> getShoppingLists() {
        return shoppingLists;
    }

    public void setShoppingLists(List<ShoppingListDto> shoppingLists) {
        if (shoppingLists != null) {
            this.shoppingLists = shoppingLists;
        }
    }
}