package com.pu.georgidinov.pupracticumvoltwo.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("user_email")
    private String user;
    @JsonProperty("items")
    private List<ItemDto> items;

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

    public ShoppingListDto items(List<ItemDto> items) {
        this.items = items;
        return this;
    }
}