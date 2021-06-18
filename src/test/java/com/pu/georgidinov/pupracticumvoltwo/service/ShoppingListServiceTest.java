package com.pu.georgidinov.pupracticumvoltwo.service;

import com.pu.georgidinov.pupracticumvoltwo.domain.Item;
import com.pu.georgidinov.pupracticumvoltwo.domain.ShoppingList;
import com.pu.georgidinov.pupracticumvoltwo.domain.UnitOfMeasure;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceNotFoundException;
import com.pu.georgidinov.pupracticumvoltwo.repository.ShoppingListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ShoppingListServiceTest {

    @Mock
    private ShoppingListRepository shoppingListRepository;
    @InjectMocks
    private ShoppingListService shoppingListService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllShoppingListsOk() {
        //given
        List<ShoppingList> lists = new ArrayList<>();
        lists.add(new ShoppingList());
        lists.add(new ShoppingList());
        when(this.shoppingListRepository.findAll()).thenReturn(lists);
        //when
        List<ShoppingList> shoppingLists = this.shoppingListService.findAllShoppingLists();
        //then
        assertNotNull(shoppingLists);
        assertFalse(shoppingLists.isEmpty());
        assertEquals(2, shoppingLists.size());
    }

    @Test
    void findAllShoppingListsNoRecords() {
        //given when
        List<ShoppingList> shoppingLists = this.shoppingListService.findAllShoppingLists();
        //then
        assertNotNull(shoppingLists);
        assertTrue(shoppingLists.isEmpty());
    }

    @Test
    void findShoppingListByIdOk() {
        //given
        Long id = 1L;
        when(this.shoppingListRepository.findById(anyLong())).thenReturn(Optional.of(new ShoppingList().id(id)));
        //when
        ShoppingList savedList = this.shoppingListService.findShoppingListById(id);
        //then
        assertNotNull(savedList);
        assertNotNull(savedList.getId());
        assertEquals(id, savedList.getId());
    }

    @Test
    void findShoppingListByIdNotFound() {
        //given
        long id = 1L;
        String exceptionMessage = "Shopping List with ID = '" + id + "' NOT FOUND";
        when(this.shoppingListRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> this.shoppingListService.findShoppingListById(id)
        );
        //then
        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void findAllShoppingListsByUserIdOk() {
        //given
        Long id = 1L;
        List<ShoppingList> lists = new ArrayList<>();
        lists.add(new ShoppingList());
        lists.add(new ShoppingList());
        when(this.shoppingListRepository.findAllByApplicationUserId(anyLong())).thenReturn(lists);
        //when
        List<ShoppingList> shoppingLists = this.shoppingListService.findAllShoppingListsByUserId(id);
        //then
        assertNotNull(shoppingLists);
        assertFalse(shoppingLists.isEmpty());
        assertEquals(2, shoppingLists.size());
    }

    @Test
    void findAllShoppingListsByUserIdNoRecords() {
        //given
        Long id = 1L;
        //when
        List<ShoppingList> shoppingLists = this.shoppingListService.findAllShoppingListsByUserId(id);
        //then
        assertNotNull(shoppingLists);
        assertTrue(shoppingLists.isEmpty());
    }

    @Test
    void saveShoppingList() {
        //given
        Long id = 1L;
        ShoppingList shoppingList = new ShoppingList();
        when(this.shoppingListRepository.save(any())).thenReturn(shoppingList.id(id));
        //when
        ShoppingList savedList = this.shoppingListService.saveShoppingList(shoppingList);
        //then
        assertNotNull(savedList);
        assertNotNull(savedList.getId());
        assertEquals(id, savedList.getId());
    }

    @Test
    void updateShoppingListOk() {
        //given
        Long id = 1L;
        ShoppingList shoppingList = new ShoppingList().id(id);
        when(this.shoppingListRepository.findById(anyLong())).thenReturn(Optional.of(shoppingList));
        when(this.shoppingListRepository.save(any())).thenReturn(shoppingList.title("test_title"));
        //when
        ShoppingList updatedList = this.shoppingListService.updateShoppingList(shoppingList, id);
        //then
        assertNotNull(updatedList);
        assertNotNull(updatedList.getId());
        assertEquals(id, updatedList.getId());
        assertNotNull(updatedList.getTitle());
        assertEquals(shoppingList.getTitle(), updatedList.getTitle());
    }

    @Test
    void updateShoppingListNotFound() {
        //given
        long id = 1L;
        String exceptionMessage = "Shopping List with ID = '" + id + "' NOT FOUND";
        when(this.shoppingListRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> this.shoppingListService.updateShoppingList(new ShoppingList(), id)
        );
        //then
        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        assertEquals(exceptionMessage, exception.getMessage());
    }


    @Test
    void addItemToShoppingList() {
        //given
        Long id = 1L;
        ShoppingList shoppingList = new ShoppingList().id(id);
        Item item = new Item().id(id).unitOfMeasure(new UnitOfMeasure());
        when(this.shoppingListRepository.findById(anyLong())).thenReturn(Optional.of(shoppingList));
        shoppingList.addItem(item);
        when(this.shoppingListRepository.save(any())).thenReturn(shoppingList);
        //when
        ShoppingList listWithNewItem = this.shoppingListService.addItemToShoppingList(item, id);
        //then
        assertNotNull(listWithNewItem);
        assertNotNull(listWithNewItem.getItems());
        assertTrue(listWithNewItem.getItems().contains(item));
    }

    @Test
    void addItemToShoppingListNotFound() {
        //given
        long id = 1L;
        String exceptionMessage = "Shopping List with ID = '" + id + "' NOT FOUND";
        when(this.shoppingListRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> this.shoppingListService.addItemToShoppingList(new Item().unitOfMeasure(new UnitOfMeasure()), id)
        );
        //then
        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        assertEquals(exceptionMessage, exception.getMessage());
    }


    @Test
    void deleteShoppingListByIdOk() {
        //given
        long id = 1L;
        when(this.shoppingListRepository.findById(anyLong())).thenReturn(Optional.of(new ShoppingList().id(id)));
        //when
        this.shoppingListService.deleteShoppingListById(id);
        //then
        verify(this.shoppingListRepository, times(1)).findById(anyLong());
        verify(this.shoppingListRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteShoppingListByIdNotFound() {
        //given
        long id = 1L;
        String exceptionMessage = "Shopping List with ID = '" + id + "' NOT FOUND";
        when(this.shoppingListRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> this.shoppingListService.deleteShoppingListById(id)
        );
        //then
        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        assertEquals(exceptionMessage, exception.getMessage());
    }

}