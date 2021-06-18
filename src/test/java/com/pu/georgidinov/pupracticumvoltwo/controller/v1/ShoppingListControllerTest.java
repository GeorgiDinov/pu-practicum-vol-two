package com.pu.georgidinov.pupracticumvoltwo.controller.v1;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.controlleradvise.ControllerExceptionHandler;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ItemDtoToItemConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ShoppingListDtoToShoppingList;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ShoppingListToShoppingListDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ItemDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ItemDtoList;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ShoppingListDto;
import com.pu.georgidinov.pupracticumvoltwo.controller.AbstractRestControllerTest;
import com.pu.georgidinov.pupracticumvoltwo.domain.ShoppingList;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceNotFoundException;
import com.pu.georgidinov.pupracticumvoltwo.service.ShoppingListService;
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

class ShoppingListControllerTest extends AbstractRestControllerTest {

    private static final String SHOPPING_LIST_CONTROLLER_BASE_URL = "http://localhost:8080/assistant/api/v1/shoppinglist";

    @Mock
    private ShoppingListService shoppingListService;
    @Mock
    private ShoppingListToShoppingListDto toShoppingListDto;
    @Mock
    private ShoppingListDtoToShoppingList toShoppingList;
    @Mock
    private ItemDtoToItemConverter toItem;
    @InjectMocks
    private ShoppingListController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void findAllShoppingLists() throws Exception {
        //given
        List<ShoppingList> shoppingLists = new ArrayList<>();
        shoppingLists.add(new ShoppingList().id(1L));
        shoppingLists.add(new ShoppingList().id(2L));
        when(this.shoppingListService.findAllShoppingLists()).thenReturn(shoppingLists);
        when(this.toShoppingListDto.convert(any())).thenReturn(new ShoppingListDto().id(1L)).thenReturn(new ShoppingListDto().id(2L));
        //when then
        mockMvc.perform(get(SHOPPING_LIST_CONTROLLER_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shopping_lists").exists())
                .andExpect(jsonPath("$.shopping_lists", hasSize(2)));
    }


    @Test
    void findShoppingListByIdOk() throws Exception {
        //given
        Long id = 1L;
        ShoppingListDto dto = new ShoppingListDto().id(id).title("test_title").user("test_user@example.com").items(new ItemDtoList());
        ShoppingList shoppingList = new ShoppingList();
        when(this.shoppingListService.findShoppingListById(anyLong())).thenReturn(shoppingList);
        when(this.toShoppingListDto.convert(any())).thenReturn(dto);

        //when then
        this.mockMvc.perform(get(SHOPPING_LIST_CONTROLLER_BASE_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.user").exists())
                .andExpect(jsonPath("$.items").exists());
    }

    @Test
    void findShoppingListByIdNotFound() throws Exception {
        //given
        long id = 1L;
        String exceptionMessage = "Shopping List with ID = '" + id + "' NOT FOUND";
        when(this.shoppingListService.findShoppingListById(anyLong()))
                .thenThrow(new ResourceNotFoundException(exceptionMessage));
        //when then
        this.mockMvc.perform(get(SHOPPING_LIST_CONTROLLER_BASE_URL + "/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error_message").exists())
                .andExpect(jsonPath("$.error_message", equalTo(exceptionMessage)));
    }

    @Test
    void findAllShoppingListsForUser() throws Exception {
        //given
        List<ShoppingList> shoppingLists = new ArrayList<>();
        shoppingLists.add(new ShoppingList().id(1L));
        shoppingLists.add(new ShoppingList().id(2L));
        when(this.shoppingListService.findAllShoppingListsByUserId(anyLong())).thenReturn(shoppingLists);
        when(this.toShoppingListDto.convert(any())).thenReturn(new ShoppingListDto().id(1L)).thenReturn(new ShoppingListDto().id(2L));
        //when then
        mockMvc.perform(get(SHOPPING_LIST_CONTROLLER_BASE_URL + "/user/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shopping_lists").exists())
                .andExpect(jsonPath("$.shopping_lists", hasSize(2)));
    }

    @Test
    void saveShoppingList() throws Exception {
        //given
        Long id = 1L;
        ShoppingListDto dto = new ShoppingListDto().id(id).title("test_title").user("test_user@example.com").items(new ItemDtoList());
        ShoppingList shoppingList = new ShoppingList();
        when(this.toShoppingList.convert(any())).thenReturn(shoppingList);
        when(this.shoppingListService.saveShoppingList(any())).thenReturn(shoppingList);
        when(this.toShoppingListDto.convert(any())).thenReturn(dto);

        //when then
        this.mockMvc.perform(post(SHOPPING_LIST_CONTROLLER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.user").exists())
                .andExpect(jsonPath("$.items").exists());
    }

    @Test
    void updateShoppingList() throws Exception {
        //given
        Long id = 1L;
        ShoppingListDto dto = new ShoppingListDto().id(id).title("test_title").user("test_user@example.com").items(new ItemDtoList());
        ShoppingList shoppingList = new ShoppingList();
        when(this.toShoppingList.convert(any())).thenReturn(shoppingList);
        when(this.shoppingListService.updateShoppingList(any(), anyLong())).thenReturn(shoppingList);
        when(this.toShoppingListDto.convert(any())).thenReturn(dto);

        //when then
        this.mockMvc.perform(put(SHOPPING_LIST_CONTROLLER_BASE_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.user").exists())
                .andExpect(jsonPath("$.items").exists());
    }

    @Test
    void updateShoppingListNotFound() throws Exception {
        //given
        Long id = 1L;
        String exceptionMessage = "Shopping List with ID = '" + id + "' NOT FOUND";
        ShoppingListDto dto = new ShoppingListDto().id(id).title("test_title").user("test_user@example.com").items(new ItemDtoList());
        when(this.toShoppingList.convert(any())).thenReturn(new ShoppingList());
        when(this.shoppingListService.updateShoppingList(any(), anyLong()))
                .thenThrow(new ResourceNotFoundException(exceptionMessage));

        //when then
        this.mockMvc.perform(put(SHOPPING_LIST_CONTROLLER_BASE_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error_message").exists())
                .andExpect(jsonPath("$.error_message", equalTo(exceptionMessage)));
    }

    @Test
    void addItemToShoppingList() throws Exception {
        //given
        Long id = 1L;
        ItemDto itemDto = new ItemDto().name("test_name").quantity(5).units("meter");
        List<ItemDto> dtos = new ArrayList<>();
        dtos.add(itemDto);
        ItemDtoList itemDtoList = new ItemDtoList(dtos);
        ShoppingListDto shoppingListDto = new ShoppingListDto().id(id).title("test_title").user("test_user@example.com").items(itemDtoList);
        ShoppingList shoppingList = new ShoppingList();
        when(this.toShoppingList.convert(any())).thenReturn(shoppingList);
        when(this.shoppingListService.addItemToShoppingList(any(), anyLong())).thenReturn(shoppingList);
        when(this.toShoppingListDto.convert(any())).thenReturn(shoppingListDto);

        //when then
        this.mockMvc.perform(post(SHOPPING_LIST_CONTROLLER_BASE_URL + "/item/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(itemDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.user").exists())
                .andExpect(jsonPath("$.items").exists());
    }

    @Test
    void deleteShoppingListByIdOk() throws Exception {
        //given
        long id = 1L;
        doNothing().when(this.shoppingListService).deleteShoppingListById(anyLong());
        //when
        this.mockMvc.perform(delete(SHOPPING_LIST_CONTROLLER_BASE_URL + "/" + id))
                .andExpect(status().isOk());
        //then
        verify(this.shoppingListService).deleteShoppingListById(anyLong());
    }

    @Test
    void deleteShoppingListByIdNotFound() throws Exception {
        //given
        long id = 1L;
        String exceptionMessage = "Shopping List with ID = '" + id + "' NOT FOUND";
        doThrow(new ResourceNotFoundException(exceptionMessage))
                .when(this.shoppingListService).deleteShoppingListById(anyLong());

        //when then
        this.mockMvc.perform(delete(SHOPPING_LIST_CONTROLLER_BASE_URL + "/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error_message").exists())
                .andExpect(jsonPath("$.error_message", equalTo(exceptionMessage)));
    }
}