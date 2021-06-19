package com.pu.georgidinov.pupracticumvoltwo.api.v1.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Long id;
    @JsonProperty("shopping_list_id")
    private Long shoppingListId;
    private String name;
    private int quantity;
    private String units;


    @Override
    public String toString() {
        return "ItemDto{" +
                "id=" + id +
                ", shoppingListId=" + shoppingListId +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", units='" + units + '\'' +
                '}';
    }

    //== builder methods ==
    public ItemDto id(Long id) {
        this.id = id;
        return this;
    }

    public ItemDto name(String name) {
        this.name = name;
        return this;
    }

    public ItemDto quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ItemDto shoppingListId(Long shoppingListId) {
        this.shoppingListId = shoppingListId;
        return this;
    }

    public ItemDto units(String uomDescription) {
        this.units = uomDescription;
        return this;
    }

}