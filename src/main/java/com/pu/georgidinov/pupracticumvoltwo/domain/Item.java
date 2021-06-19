package com.pu.georgidinov.pupracticumvoltwo.domain;

import com.pu.georgidinov.pupracticumvoltwo.baseentity.BaseEntity;
import com.pu.georgidinov.pupracticumvoltwo.baseentity.BaseNamedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item implements BaseEntity, BaseNamedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure unitOfMeasure;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void assignToShoppingList(ShoppingList shoppingList) {
        if (shoppingList != null) {
            shoppingList.addItem(this);
        }
    }

    @Override
    public String toString() {
        String uom = unitOfMeasure != null ? unitOfMeasure.getDescription() : "";
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", units=" + uom +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return 23 + id.hashCode();
    }

    //== builder methods ==
    public Item id(Long id) {
        this.id = id;
        return this;
    }

    public Item name(String name) {
        this.name = name;
        return this;
    }

    public Item quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Item shoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
        return this;
    }

    public Item unitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
        return this;
    }
}