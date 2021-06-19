package com.pu.georgidinov.pupracticumvoltwo.repository;

import com.pu.georgidinov.pupracticumvoltwo.domain.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    List<Item> findItemsByShoppingListId(Long id);

}