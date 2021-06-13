package com.pu.georgidinov.pupracticumvoltwo.api.v1.dto;


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
    private String name;
    private int quantity;
    private String shoppingListName;
    private String units;


    @Override
    public String toString() {
        return "ItemDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", shoppingListName='" + shoppingListName + '\'' +
                ", uomDescription='" + units + '\'' +
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

    public ItemDto shoppingListName(String shoppingListName) {
        this.shoppingListName = shoppingListName;
        return this;
    }

    public ItemDto units(String uomDescription) {
        this.units = uomDescription;
        return this;
    }

}