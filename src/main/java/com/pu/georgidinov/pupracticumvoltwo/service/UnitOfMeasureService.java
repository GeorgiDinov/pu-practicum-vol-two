package com.pu.georgidinov.pupracticumvoltwo.service;

import com.pu.georgidinov.pupracticumvoltwo.domain.UnitOfMeasure;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceAlreadyExistsException;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceNotFoundException;
import com.pu.georgidinov.pupracticumvoltwo.repository.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasure findUnitOfMeasureById(Long id) {
        log.info("UnitOfMeasureService::findUnitOfMeasureById, ID passed = {}", id);
        Optional<UnitOfMeasure> uomOptional = this.unitOfMeasureRepository.findById(id);
        return uomOptional.orElseThrow(() -> new ResourceNotFoundException("Unit of measure with id = '" + id + "' NOT FOUND"));
    }

    public UnitOfMeasure findUnitOfMeasureByDescription(String description) {
        log.info("UnitOfMeasureService::findUnitOfMeasureByDescription");
        Optional<UnitOfMeasure> uomOptional = this.unitOfMeasureRepository.findByDescription(description);
        return uomOptional.orElseThrow(() -> new ResourceNotFoundException("Unit of measure with description = '" + description + "' NOT FOUND"));
    }

    public UnitOfMeasure saveUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        log.info("UnitOfMeasureService::saveUnitOfMeasure");
        String uomDescription = unitOfMeasure.getDescription();
        if (isUnitOfMeasureWithDescriptionExists(uomDescription)) {
            throw new ResourceAlreadyExistsException("Unit of measure with description = '" + uomDescription + "' already exists");
        }
        return this.unitOfMeasureRepository.save(unitOfMeasure);
    }

    public List<UnitOfMeasure> findAllUnitsOfMeasure() {
        log.info("UnitOfMeasureService::findAllUnitOfMeasures");
        List<UnitOfMeasure> unitOfMeasureList = new ArrayList<>();
        this.unitOfMeasureRepository.findAll().forEach(unitOfMeasureList::add);
        return unitOfMeasureList;
    }

    public UnitOfMeasure updateUnitOfMeasure(UnitOfMeasure unitOfMeasure, Long id) {
        log.info("UnitOfMeasureService::updateUnitOfMeasure, ID passed = {}", id);

        String updatedDescription = unitOfMeasure.getDescription();
        return this.unitOfMeasureRepository.findById(id)
                .map(uom -> this.saveUnitOfMeasure(uom.description(updatedDescription)))
                .orElseThrow(() -> new ResourceNotFoundException("Unit of measure with id = '" + id + "' NOT FOUND"));
    }

    public void deleteUnitOfMeasureById(Long id) {
        log.info("UnitOfMeasureService::deleteUnitOfMeasureById, ID passed = {}", id);
        if (!isUnitOfMeasureWithIdExists(id)) {
            throw new ResourceNotFoundException("Unit of measure with id = '" + id + "' NOT FOUND");
        }
        this.unitOfMeasureRepository.deleteById(id);
    }

    private boolean isUnitOfMeasureWithDescriptionExists(String description) {
        return this.unitOfMeasureRepository.findByDescription(description).isPresent();
    }

    private boolean isUnitOfMeasureWithIdExists(Long id) {
        return this.unitOfMeasureRepository.findById(id).isPresent();
    }

}