package com.pu.georgidinov.pupracticumvoltwo.domain;

import com.pu.georgidinov.pupracticumvoltwo.baseentity.BaseEntity;
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
public class ApplicationUser implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "application_user_credentials_id")
    private ApplicationUserCredentials applicationUserCredentials;

    @OneToMany(mappedBy = "applicationUser", cascade = CascadeType.ALL)
    private Set<ShoppingList> shoppingLists = new HashSet<>();

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApplicationUser that = (ApplicationUser) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 17 + id.hashCode();
    }

    //== builder methods ==
    public ApplicationUser id(Long id) {
        this.id = id;
        return this;
    }

    public ApplicationUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ApplicationUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ApplicationUser credentials(ApplicationUserCredentials credentials) {
        this.applicationUserCredentials = credentials;
        return this;
    }

    public ApplicationUser shoppingLists(Set<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
        return this;
    }
}