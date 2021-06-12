package com.pu.georgidinov.pupracticumvoltwo.domain;

import com.pu.georgidinov.pupracticumvoltwo.baseentity.BaseEntity;
import com.pu.georgidinov.pupracticumvoltwo.baseentity.BaseNamedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShoppingList implements BaseEntity, BaseNamedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToOne
    @JoinColumn(name = "application_user_id")
    private ApplicationUser applicationUser;

    @OneToMany(mappedBy = "shoppingList")
    private Set<Item> items = new HashSet<>();

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShoppingList that = (ShoppingList) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31 + id.hashCode();
    }

    //== builder methods ==
    public ShoppingList id(Long id) {
        this.id = id;
        return this;
    }

    public ShoppingList title(String title) {
        this.title = title;
        return this;
    }

    public ShoppingList applicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
        return this;
    }

    public ShoppingList items(Set<Item> items) {
        this.items = items;
        return this;
    }
}