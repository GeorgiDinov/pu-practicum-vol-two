package com.pu.georgidinov.pupracticumvoltwo.repository;

import com.pu.georgidinov.pupracticumvoltwo.domain.ShoppingList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends CrudRepository<ShoppingList, Long> {

}