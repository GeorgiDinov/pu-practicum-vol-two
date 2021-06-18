package com.pu.georgidinov.pupracticumvoltwo.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListDto {

    private Long id;
    private String title;
    private String user;
    private ItemDtoList items;

    @Override
    public String toString() {
        return "ShoppingListDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", user=" + user +
                '}';
    }

    //== builder methods ==
    public ShoppingListDto id(Long id) {
        this.id = id;
        return this;
    }

    public ShoppingListDto title(String title) {
        this.title = title;
        return this;
    }

    public ShoppingListDto user(String user) {
        this.user = user;
        return this;
    }

    public ShoppingListDto items(ItemDtoList items) {
        this.items = items;
        return this;
    }
}