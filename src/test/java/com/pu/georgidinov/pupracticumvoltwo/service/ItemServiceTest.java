package com.pu.georgidinov.pupracticumvoltwo.service;

import com.pu.georgidinov.pupracticumvoltwo.domain.Item;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceNotFoundException;
import com.pu.georgidinov.pupracticumvoltwo.repository.ItemRepository;
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

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllItems() {
        //given
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        when(this.itemRepository.findAll()).thenReturn(items);
        //when
        List<Item> itemList = this.itemService.findAllItems();
        //then
        assertNotNull(itemList);
        assertFalse(itemList.isEmpty());
        assertEquals(2, itemList.size());
    }

    @Test
    void findAllItemsNoRecords() {
        //when
        List<Item> itemList = this.itemService.findAllItems();
        //then
        assertNotNull(itemList);
        assertTrue(itemList.isEmpty());
    }

    @Test
    void findAllItemsByShoppingListId() {
        //given
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        when(this.itemRepository.findItemsByShoppingListId(anyLong())).thenReturn(items);
        //when
        List<Item> itemList = this.itemService.findAllItemsByShoppingListId(1L);
        //then
        assertNotNull(itemList);
        assertFalse(itemList.isEmpty());
        assertEquals(2, itemList.size());
    }

    @Test
    void findAllItemsByShoppingListIdNoRecords() {
        //when
        List<Item> itemList = this.itemService.findAllItemsByShoppingListId(1L);
        //then
        assertNotNull(itemList);
        assertTrue(itemList.isEmpty());
    }

    @Test
    void findItemByItemId() {
        //given
        Long id = 1L;
        Item item = new Item().id(id);
        when(this.itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        //when
        Item foundItem = this.itemService.findItemByItemId(id);
        //then
        assertNotNull(foundItem);
        assertNotNull(foundItem.getId());
        assertEquals(id, foundItem.getId());
    }

    @Test
    void findItemByItemIdNotFound() {
        //given
        long id = 1L;
        String exceptionMessage = "Item with id = '" + id + "' NOT FOUND";
        when(this.itemRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> this.itemService.findItemByItemId(id)
        );
        //then
        assertNotNull(exception);
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void saveItem() {
        //given
        Item item = new Item().id(1L);
        when(this.itemRepository.save(any())).thenReturn(item);
        //when
        Item savedItem = this.itemService.saveItem(item);
        //then
        assertNotNull(savedItem);
        assertEquals(item.getId(), savedItem.getId());
    }

    @Test
    void updateItem() {
        //given
        Long id = 1L;
        Item item = new Item().id(id).name("updated_name");
        Item itemToUpdate = new Item().id(id).name("name");
        when(this.itemRepository.findById(anyLong())).thenReturn(Optional.of(itemToUpdate));
        when(this.itemRepository.save(any())).thenReturn(item);
        //when
        Item updateItem = this.itemService.updateItem(item, id);
        //then
        assertNotNull(updateItem);
        assertNotNull(updateItem.getName());
        assertEquals(item.getName(), updateItem.getName());
    }

    @Test
    void updateItemNotFound() {
        //given
        long id = 1L;
        Item item = new Item().id(id).name("updated_name");
        String exceptionMessage = "Item with id = '" + id + "' NOT FOUND";
        when(this.itemRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> this.itemService.updateItem(item, id)
        );
        //then
        assertNotNull(exception);
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void deleteItemById() {
        //given
        when(this.itemRepository.findById(anyLong())).thenReturn(Optional.of(new Item().id(1L)));
        //when
        this.itemService.deleteItemById(1L);
        //then
        verify(this.itemRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteItemByIdNotFound() {
        //given
        long id = 1L;
        String exceptionMessage = "Item with id = '" + id + "' NOT FOUND";
        when(this.itemRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> this.itemService.deleteItemById(id)
        );
        //then
        assertNotNull(exception);
        assertEquals(exceptionMessage, exception.getMessage());
    }
}