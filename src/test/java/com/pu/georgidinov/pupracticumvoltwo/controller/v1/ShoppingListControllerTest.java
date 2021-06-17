package com.pu.georgidinov.pupracticumvoltwo.controller.v1;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.controlleradvise.ControllerExceptionHandler;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ShoppingListDtoToShoppingList;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ShoppingListToShoppingListDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ShoppingListDto;
import com.pu.georgidinov.pupracticumvoltwo.domain.ShoppingList;
import com.pu.georgidinov.pupracticumvoltwo.service.ShoppingListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShoppingListControllerTest {

    private static final String SHOPPING_LIST_CONTROLLER_BASE_URL = "http://localhost:8080/assistant/api/v1/shoppinglist";

    @Mock
    private ShoppingListService shoppingListService;
    @Mock
    private ShoppingListToShoppingListDto toShoppingListDto;
    @Mock
    private ShoppingListDtoToShoppingList toShoppingList;
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
    void findAllShoppingListsForUser() throws Exception {
        //given
        List<ShoppingList> shoppingLists = new ArrayList<>();
        shoppingLists.add(new ShoppingList().id(1L));
        shoppingLists.add(new ShoppingList().id(2L));
        when(this.shoppingListService.findAllShoppingListsByUserId(anyLong())).thenReturn(shoppingLists);
        when(this.toShoppingListDto.convert(any())).thenReturn(new ShoppingListDto().id(1L)).thenReturn(new ShoppingListDto().id(2L));
        //when then
        mockMvc.perform(get(SHOPPING_LIST_CONTROLLER_BASE_URL + "/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shopping_lists").exists())
                .andExpect(jsonPath("$.shopping_lists", hasSize(2)));
    }
}