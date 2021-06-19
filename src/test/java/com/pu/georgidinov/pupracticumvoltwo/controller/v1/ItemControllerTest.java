package com.pu.georgidinov.pupracticumvoltwo.controller.v1;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.controlleradvise.ControllerExceptionHandler;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ItemDtoToItemConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ItemToItemDtoConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ItemDto;
import com.pu.georgidinov.pupracticumvoltwo.controller.AbstractRestControllerTest;
import com.pu.georgidinov.pupracticumvoltwo.domain.Item;
import com.pu.georgidinov.pupracticumvoltwo.domain.UnitOfMeasure;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceNotFoundException;
import com.pu.georgidinov.pupracticumvoltwo.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ItemControllerTest extends AbstractRestControllerTest {

    private static final String ITEM_CONTROLLER_BASE_URL = "http://localhost:8080/assistant/api/v1/item";

    @Mock
    private ItemService itemService;
    @Mock
    private ItemToItemDtoConverter toItemDto;
    @Mock
    private ItemDtoToItemConverter toItem;
    @InjectMocks
    private ItemController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(this.controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void findAllItems() throws Exception {
        //given
        List<Item> items = new ArrayList<>();
        items.add(new Item().id(1L).name("tst_item_1").quantity(5).unitOfMeasure(new UnitOfMeasure(1L, "tst_uom_1")));
        items.add(new Item().id(2L).name("tst_item_2").quantity(10).unitOfMeasure(new UnitOfMeasure(2L, "tst_uom_2")));
        when(this.itemService.findAllItems()).thenReturn(items);
        //when then
        this.mockMvc.perform(get(ITEM_CONTROLLER_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").exists())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(2)));
    }

    @Test
    void findAllItemsForShoppingList() throws Exception {
        //given
        List<Item> items = new ArrayList<>();
        items.add(new Item().id(1L).name("tst_item_1").quantity(5).unitOfMeasure(new UnitOfMeasure(1L, "tst_uom_1")));
        items.add(new Item().id(2L).name("tst_item_2").quantity(10).unitOfMeasure(new UnitOfMeasure(2L, "tst_uom_2")));
        when(this.itemService.findAllItemsByShoppingListId(anyLong())).thenReturn(items);
        //when then
        this.mockMvc.perform(get(ITEM_CONTROLLER_BASE_URL + "/inlist/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").exists())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(2)));
    }

    @Test
    void findItemByIdOk() throws Exception {
        //given
        Item item = new Item().id(1L).name("tst_item_1").quantity(5).unitOfMeasure(new UnitOfMeasure(1L, "tst_uom_1"));
        when(this.itemService.findItemByItemId(anyLong())).thenReturn(item);
        when(this.toItemDto.convert(any()))
                .thenReturn(new ItemDto().id(item.getId()).shoppingListId(1L).name(item.getName()).quantity(item.getQuantity()).units(item.getUnitOfMeasure().getDescription()));
        //when then
        this.mockMvc.perform(get(ITEM_CONTROLLER_BASE_URL + "/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.shopping_list_id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.quantity").exists())
                .andExpect(jsonPath("$.units").exists());
    }

    @Test
    void findItemByIdNotFound() throws Exception {
        //given
        long id = 1L;
        String exceptionMessage = "Item with id = '" + id + "' NOT FOUND";
        when(this.itemService.findItemByItemId(anyLong()))
                .thenThrow(new ResourceNotFoundException(exceptionMessage));

        //when then
        this.mockMvc.perform(get(ITEM_CONTROLLER_BASE_URL + "/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error_message").exists())
                .andExpect(jsonPath("$.error_message", equalTo(exceptionMessage)));
    }

    @Test
    void saveItemOk() throws Exception {
        //given
        Long id = 1L;
        Item item = new Item().name("tst_item_1").quantity(5).unitOfMeasure(new UnitOfMeasure(1L, "tst_uom_1"));
        ItemDto itemDto = new ItemDto().name(item.getName()).quantity(item.getQuantity()).units(item.getUnitOfMeasure().getDescription());

        when(this.toItem.convert(any())).thenReturn(item);
        item.setId(id);
        when(this.itemService.saveItem(any())).thenReturn(item);
        itemDto.setId(id);
        itemDto.setShoppingListId(id);
        when(this.toItemDto.convert(any())).thenReturn(itemDto);

        //when then
        this.mockMvc.perform(post(ITEM_CONTROLLER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(itemDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.shopping_list_id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.quantity").exists())
                .andExpect(jsonPath("$.units").exists());
    }

    @Test
    void updateItem() throws Exception {
        //given
        Long id = 1L;
        Item item = new Item().name("tst_item_1").quantity(5).unitOfMeasure(new UnitOfMeasure(1L, "tst_uom_1"));
        ItemDto itemDto = new ItemDto().name(item.getName()).quantity(item.getQuantity()).units(item.getUnitOfMeasure().getDescription());
        when(this.toItem.convert(any())).thenReturn(item);
        when(this.itemService.updateItem(any(), anyLong())).thenReturn(item);
        itemDto.setId(id);
        itemDto.shoppingListId(id);
        when(this.toItemDto.convert(any())).thenReturn(itemDto);
        //when then
        this.mockMvc.perform(put(ITEM_CONTROLLER_BASE_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(itemDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.shopping_list_id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.quantity").exists())
                .andExpect(jsonPath("$.units").exists());
    }

    @Test
    void deleteItem() throws Exception {
        this.mockMvc.perform(delete(ITEM_CONTROLLER_BASE_URL + "/" + 1))
                .andExpect(status().isOk());
        verify(this.itemService, times(1)).deleteItemById(anyLong());
    }

    @Test
    void deleteItemNotFound() throws Exception {
        //given
        long id = 1L;
        String exceptionMessage = "Item with id = '" + id + "' NOT FOUND";
        doThrow(new ResourceNotFoundException(exceptionMessage)).when(this.itemService).deleteItemById(anyLong());
        //when
        this.mockMvc.perform(delete(ITEM_CONTROLLER_BASE_URL + "/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error_message").exists())
                .andExpect(jsonPath("$.error_message", equalTo(exceptionMessage)));
        //then
        verify(this.itemService, times(1)).deleteItemById(anyLong());
    }
}