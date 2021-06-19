package com.pu.georgidinov.pupracticumvoltwo.controller.v1;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.controlleradvise.ControllerExceptionHandler;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.UnitOfMeasureToUomDtoConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.UomDtoToUnitOfMeasureConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.UomDto;
import com.pu.georgidinov.pupracticumvoltwo.controller.AbstractRestControllerTest;
import com.pu.georgidinov.pupracticumvoltwo.domain.UnitOfMeasure;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceAlreadyExistsException;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceNotFoundException;
import com.pu.georgidinov.pupracticumvoltwo.service.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UnitOfMeasureControllerTest extends AbstractRestControllerTest {

    private static final String UOM_CONTROLLER_BASE_URL = "http://localhost:8080/assistant/api/v1/uom";

    @Mock
    private UnitOfMeasureService unitOfMeasureService;
    @Mock
    private UnitOfMeasureToUomDtoConverter toUomDto;
    @Mock
    private UomDtoToUnitOfMeasureConverter toUnitOfMeasure;
    @InjectMocks
    private UnitOfMeasureController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(this.controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void findAllUnitsOfMeasure() throws Exception {
        //when then
        this.mockMvc.perform(get(UOM_CONTROLLER_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.units").exists());
    }

    @Test
    void findUnitOfMeasureByIdOk() throws Exception {
        //given
        long id = 1L;
        UnitOfMeasure uom = new UnitOfMeasure(id, "mock_description");
        when(this.unitOfMeasureService.findUnitOfMeasureById(anyLong())).thenReturn(uom);
        when(this.toUomDto.convert(any())).thenReturn(new UomDto(uom.getId(), uom.getDescription()));

        //when then
        this.mockMvc.perform(get(UOM_CONTROLLER_BASE_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.description", equalTo("mock_description")));
    }

    @Test
    void findUnitOfMeasureByIdNotFound() throws Exception {
        //given
        long id = 1L;
        when(this.unitOfMeasureService.findUnitOfMeasureById(anyLong()))
                .thenThrow(new ResourceNotFoundException("Unit of measure with id = " + id + " NOT FOUND"));
        //when then
        this.mockMvc.perform(get(UOM_CONTROLLER_BASE_URL + "/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error_message").exists())
                .andExpect(jsonPath("$.error_message", equalTo("Unit of measure with id = " + id + " NOT FOUND")));
    }

    @Test
    void findUnitOfMeasureByDescriptionOk() throws Exception {
        //given
        long id = 1L;
        String description = "mock_description";
        UnitOfMeasure uom = new UnitOfMeasure(id, description);
        when(this.unitOfMeasureService.findUnitOfMeasureByDescription(anyString())).thenReturn(uom);
        when(this.toUomDto.convert(any())).thenReturn(new UomDto(uom.getId(), uom.getDescription()));

        //when then
        this.mockMvc.perform(get(UOM_CONTROLLER_BASE_URL + "/description/" + description))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.description", equalTo(description)));
    }

    @Test
    void findUnitOfMeasureByDescriptionNotFound() throws Exception {
        //given
        String description = "mock_description";
        String exceptionMessage = "Unit of measure with description = " + description + " NOT FOUND";
        when(this.unitOfMeasureService.findUnitOfMeasureByDescription(anyString()))
                .thenThrow(new ResourceNotFoundException(exceptionMessage));
        //when then
        this.mockMvc.perform(get(UOM_CONTROLLER_BASE_URL + "/description/" + description))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error_message").exists())
                .andExpect(jsonPath("$.error_message", equalTo(exceptionMessage)));
    }


    @Test
    void saveUnitOfMeasureOk() throws Exception {
        //given
        UomDto uomDto = new UomDto().description("mock_description");
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure(1L, uomDto.getDescription());
        when(this.toUnitOfMeasure.convert(any())).thenReturn(new UnitOfMeasure().description(uomDto.getDescription()));
        when(this.unitOfMeasureService.saveUnitOfMeasure(any())).thenReturn(unitOfMeasure);
        uomDto.setId(unitOfMeasure.getId());
        when(this.toUomDto.convert(any())).thenReturn(uomDto);
        //when then
        this.mockMvc.perform(post(UOM_CONTROLLER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(uomDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.description", equalTo(uomDto.getDescription())));
    }

    @Test
    void saveUnitOfMeasureAlreadyExists() throws Exception {
        //given
        UomDto uomDto = new UomDto().description("mock_description");
        String exceptionMessage = "Unit of measure with description = '" + uomDto.getDescription() + "' already exists";
        when(this.toUnitOfMeasure.convert(any()))
                .thenReturn(new UnitOfMeasure().description(uomDto.getDescription()));

        when(this.unitOfMeasureService.saveUnitOfMeasure(any()))
                .thenThrow(new ResourceAlreadyExistsException(exceptionMessage));
        //when then
        this.mockMvc.perform(post(UOM_CONTROLLER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(uomDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error_message").exists())
                .andExpect(jsonPath("$.error_message", equalTo(exceptionMessage)));
    }

    @Test
    void updateUnitOfMeasureOk() throws Exception {
        //given
        long id = 1L;
        String updatedDescription = "mock_description_updated";
        UomDto uomDto = new UomDto().description(updatedDescription);
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure(id, updatedDescription);
        when(this.toUnitOfMeasure.convert(any())).thenReturn(new UnitOfMeasure().description(updatedDescription));
        when(this.unitOfMeasureService.updateUnitOfMeasure(any(), anyLong())).thenReturn(unitOfMeasure);
        uomDto.setId(id);
        when(this.toUomDto.convert(any())).thenReturn(uomDto);
        //when then
        this.mockMvc.perform(put(UOM_CONTROLLER_BASE_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(uomDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.description", equalTo(uomDto.getDescription())));
    }

    @Test
    void updateUnitOfMeasureIdNotFound() throws Exception {
        //given
        long id = 1L;
        String exceptionMessage = "Unit of measure with id = " + id + " NOT FOUND";
        UomDto uomDto = new UomDto().description("mock_description_updated");
        when(this.toUnitOfMeasure.convert(any())).thenReturn(new UnitOfMeasure().description("mock_description_updated"));
        when(this.unitOfMeasureService.updateUnitOfMeasure(any(), anyLong()))
                .thenThrow(new ResourceNotFoundException(exceptionMessage));
        //when then
        this.mockMvc.perform(put(UOM_CONTROLLER_BASE_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(uomDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error_message").exists())
                .andExpect(jsonPath("$.error_message", equalTo(exceptionMessage)));
    }

    @Test
    void updateUnitOfMeasureDescriptionExists() throws Exception {
        //given
        long id = 1L;
        UomDto uomDto = new UomDto().description("mock_description_updated");
        String exceptionMessage = "Unit of measure with description = '" + uomDto.getDescription() + "' already exists";

        when(this.toUnitOfMeasure.convert(any()))
                .thenReturn(new UnitOfMeasure().description("mock_description"));
        when(this.unitOfMeasureService.updateUnitOfMeasure(any(), anyLong()))
                .thenThrow(new ResourceAlreadyExistsException(exceptionMessage));
        //when then
        this.mockMvc.perform(put(UOM_CONTROLLER_BASE_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(uomDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error_message").exists())
                .andExpect(jsonPath("$.error_message", equalTo(exceptionMessage)));
    }

    @Test
    void deleteUnitOfMeasureByIdOk() throws Exception {
        long id = 1L;
        doNothing().when(unitOfMeasureService).deleteUnitOfMeasureById(anyLong());
        this.mockMvc.perform(delete(UOM_CONTROLLER_BASE_URL + "/" + id))
                .andExpect(status().isOk());
        verify(unitOfMeasureService, times(1)).deleteUnitOfMeasureById(anyLong());
    }

    @Test
    void deleteUnitOfMeasureByIdNotFound() throws Exception {
        //given
        long id = 1L;
        String exceptionMessage = "Unit of measure with id = '" + id + "' NOT FOUND";
        doThrow(new ResourceNotFoundException(exceptionMessage))
                .when(this.unitOfMeasureService)
                .deleteUnitOfMeasureById(anyLong());
        //when then
        this.mockMvc.perform(delete(UOM_CONTROLLER_BASE_URL + "/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error_message").exists())
                .andExpect(jsonPath("$.error_message", equalTo(exceptionMessage)));

        verify(unitOfMeasureService, times(1)).deleteUnitOfMeasureById(anyLong());
    }
}