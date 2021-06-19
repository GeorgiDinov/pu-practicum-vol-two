package com.pu.georgidinov.pupracticumvoltwo.service;

import com.pu.georgidinov.pupracticumvoltwo.domain.UnitOfMeasure;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceAlreadyExistsException;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceNotFoundException;
import com.pu.georgidinov.pupracticumvoltwo.repository.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UnitOfMeasureServiceTest {

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;
    @InjectMocks
    private UnitOfMeasureService unitOfMeasureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findUnitOfMeasureByIdOk() {
        //given
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure(1L, "mock_description");
        when(this.unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.of(unitOfMeasure));
        //when
        UnitOfMeasure uom = this.unitOfMeasureService.findUnitOfMeasureById(1L);
        //then
        assertNotNull(uom);
        assertNotNull(uom.getId());
        assertEquals(1L, uom.getId());
        assertNotNull(uom.getDescription());
        assertEquals("mock_description", uom.getDescription());
    }

    @Test
    void findUnitOfMeasureByIdNotFound() {
        //given
        long id = 1L;
        when(this.unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.empty());
        String expectedExceptionMessage = "Unit of measure with id = '" + id + "' NOT FOUND";
        //when
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> this.unitOfMeasureService.findUnitOfMeasureById(id)
        );
        //then
        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    void findUnitOfMeasureByDescriptionOk() {
        //given
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure(1L, "mock_description");
        when(this.unitOfMeasureRepository.findByDescription(anyString())).thenReturn(Optional.of(unitOfMeasure));
        //when
        UnitOfMeasure uom = this.unitOfMeasureService.findUnitOfMeasureByDescription("mock_description");
        //then
        assertNotNull(uom);
        assertNotNull(uom.getId());
        assertEquals(1L, uom.getId());
        assertNotNull(uom.getDescription());
        assertEquals("mock_description", uom.getDescription());
    }

    @Test
    void findUnitOfMeasureByDescriptionNotFound() {
        //given
        String description = "mock_description";
        when(this.unitOfMeasureRepository.findByDescription(anyString())).thenReturn(Optional.empty());
        String expectedExceptionMessage = "Unit of measure with description = '" + description + "' NOT FOUND";
        //when
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> this.unitOfMeasureService.findUnitOfMeasureByDescription(description)
        );
        //then
        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    void saveUnitOfMeasureOk() {
        //given
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure(1L, "mock_description");
        when(this.unitOfMeasureRepository.findByDescription(anyString())).thenReturn(Optional.empty());
        when(this.unitOfMeasureRepository.save(any())).thenReturn(unitOfMeasure);
        //when
        UnitOfMeasure savedUom = this.unitOfMeasureService.saveUnitOfMeasure(unitOfMeasure);
        //then
        assertNotNull(savedUom);
        assertNotNull(savedUom.getId());
        assertNotNull(savedUom.getDescription());
        assertEquals(unitOfMeasure.getId(), savedUom.getId());
        assertEquals(unitOfMeasure.getDescription(), savedUom.getDescription());
    }

    @Test
    void saveUnitOfMeasureAlreadyExists() {
        //given
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure(1L, "mock_description");
        String expectedExceptionMessage = "Unit of measure with description = '" + unitOfMeasure.getDescription() + "' already exists";
        when(this.unitOfMeasureRepository.findByDescription(anyString())).thenReturn(Optional.of(unitOfMeasure));
        //when
        Exception exception = assertThrows(ResourceAlreadyExistsException.class,
                () -> this.unitOfMeasureService.saveUnitOfMeasure(unitOfMeasure)
        );
        //then
        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    void findAllUnitOfMeasures() {
        //given
        List<UnitOfMeasure> unitOfMeasureList = new ArrayList<>();
        unitOfMeasureList.add(new UnitOfMeasure(1L, "mock_description_one"));
        unitOfMeasureList.add(new UnitOfMeasure(2L, "mock_description_two"));
        when(this.unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasureList);
        //when
        List<UnitOfMeasure> fetchedUom = this.unitOfMeasureService.findAllUnitsOfMeasure();
        //then
        assertNotNull(fetchedUom);
        assertEquals(unitOfMeasureList.size(), fetchedUom.size());
        assertNotNull(fetchedUom.get(0));
        assertNotNull(fetchedUom.get(1));
    }

    @Test
    void findAllUnitOfMeasuresNoRecords() {
        //when
        List<UnitOfMeasure> fetchedUom = this.unitOfMeasureService.findAllUnitsOfMeasure();
        //then
        assertNotNull(fetchedUom);
        assertTrue(fetchedUom.isEmpty());
    }

    @Test
    void updateUnitOfMeasureOk() {
        //given
        String updatedDescription = "mock_description_updated";
        UnitOfMeasure uomToUpdate = new UnitOfMeasure(1L, updatedDescription);
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure(1L, "mock_description");
        when(this.unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.of(unitOfMeasure));
        when(this.unitOfMeasureRepository.findByDescription(anyString())).thenReturn(Optional.empty());
        when(this.unitOfMeasureRepository.save(any())).thenReturn(uomToUpdate);
        //when
        UnitOfMeasure updatedUom = this.unitOfMeasureService.updateUnitOfMeasure(uomToUpdate, 1L);
        //then
        assertNotNull(updatedUom);
        assertNotNull(updatedUom.getDescription());
        assertEquals(updatedDescription, updatedUom.getDescription());
    }

    @Test
    void updateUnitOfMeasureNotFound() {
        //given
        long id = 1L;
        String updatedDescription = "mock_description_updated";
        UnitOfMeasure uomToUpdate = new UnitOfMeasure(id, updatedDescription);
        when(this.unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.empty());
        String expectedExceptionMessage = "Unit of measure with id = '" + id + "' NOT FOUND";
        //when
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> this.unitOfMeasureService.updateUnitOfMeasure(uomToUpdate, id)
        );
        //then
        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    void updateUnitOfMeasureDescriptionExists() {
        //given
        long id = 1L;
        String updatedDescription = "mock_description_updated";
        UnitOfMeasure uomToUpdate = new UnitOfMeasure(id, updatedDescription);
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure(id, "mock_description");
        when(this.unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.of(unitOfMeasure));
        when(this.unitOfMeasureRepository.findByDescription(anyString())).thenReturn(Optional.of(uomToUpdate));
        String expectedExceptionMessage = "Unit of measure with description = '" + updatedDescription + "' already exists";
        //when
        Exception exception = assertThrows(ResourceAlreadyExistsException.class,
                () -> this.unitOfMeasureService.updateUnitOfMeasure(uomToUpdate, id)
        );
        //then
        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

}