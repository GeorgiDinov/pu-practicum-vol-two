package com.pu.georgidinov.pupracticumvoltwo.api.v1.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ItemDtoList {

    @JsonProperty("items")
    private List<ItemDto> itemDtos = new ArrayList<>();

    public ItemDtoList(List<ItemDto> itemDtos) {
        this.setItemDtos(itemDtos);
    }

    public List<ItemDto> getItemDtos() {
        return itemDtos;
    }

    public void setItemDtos(List<ItemDto> itemDtos) {
        if (itemDtos != null) {
            this.itemDtos = itemDtos;
        }
    }

}