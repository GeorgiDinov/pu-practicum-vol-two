package com.pu.georgidinov.pupracticumvoltwo.controller.v1;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.UnitOfMeasureToUomDtoConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.UomDtoToUnitOfMeasureConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.UomDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.UomDtoList;
import com.pu.georgidinov.pupracticumvoltwo.domain.UnitOfMeasure;
import com.pu.georgidinov.pupracticumvoltwo.service.UnitOfMeasureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("assistant/api/v1/uom")
@RequiredArgsConstructor
public class UnitOfMeasureController {

    private final UnitOfMeasureService unitOfMeasureService;
    private final UnitOfMeasureToUomDtoConverter toUomDto;
    private final UomDtoToUnitOfMeasureConverter toUnitOfMeasure;


    @Operation(summary = "This Operation Retrieves All Units Of Measure Stored In DB")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Retrieves All Units From DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UomDtoList findAllUnitsOfMeasure() {
        log.info("UnitOfMeasureController::fetchAllUnitsOfMeasure");
        List<UomDto> uomDtos = this.unitOfMeasureService.findAllUnitsOfMeasure().stream()
                .map(toUomDto::convert)
                .collect(Collectors.toList());
        return new UomDtoList(uomDtos);
    }


    @Operation(summary = "This Operation Retrieves Single Unit Of Measure Stored In DB By The Unit Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Retrieves Single Unit From DB",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Returns Error Message If Unit Not Found In DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UomDto findUnitOfMeasureById(@PathVariable Long id) {
        log.info("UnitOfMeasureController::findUnitOfMeasureById, ID passed = {}", id);
        return this.toUomDto.convert(this.unitOfMeasureService.findUnitOfMeasureById(id));
    }


    @Operation(summary = "This Operation Retrieves Single Unit Of Measure Stored In DB By The Unit Description")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Retrieves Single Unit From DB",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Returns Error Message If Unit Not Found In DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @GetMapping("/description/{description}")
    @ResponseStatus(HttpStatus.OK)
    public UomDto findUnitOfMeasureByDescription(@PathVariable String description) {
        log.info("UnitOfMeasureController::findUnitOfMeasureByDescription, Description passed = {}", description);
        return this.toUomDto.convert(this.unitOfMeasureService.findUnitOfMeasureByDescription(description));
    }


    @Operation(summary = "This Operation Creates Single Unit Of Measure And Stores It In The DB")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201",
                            description = "Creates Single Unit And Store It In The DB",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "409",
                            description = "Returns Error Message If Unit With The Same Description Exists In The DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UomDto saveUnitOfMeasure(@RequestBody UomDto uomDto) {
        log.info("UnitOfMeasureController::saveUnitOfMeasure, DTO passed = {}", uomDto);
        UnitOfMeasure uom = this.toUnitOfMeasure.convert(uomDto);
        UnitOfMeasure savedUom = this.unitOfMeasureService.saveUnitOfMeasure(uom);
        return toUomDto.convert(savedUom);
    }


    @Operation(summary = "This Operation Updates Single Unit Of Measure Description Value")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Updates Single Unit Description Value And Store It In The DB",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Returns Error Message If Unit With The Passed ID Not Found In DB",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "409",
                            description = "Returns Error Message If Unit With The Same Description Exists In The DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UomDto updateUnitOfMeasure(@RequestBody UomDto uomDto, @PathVariable Long id) {
        log.info("UnitOfMeasureController::updateUnitOfMeasure, DTO passed = {}, ID passed = {}", uomDto, id);
        UnitOfMeasure uom = this.toUnitOfMeasure.convert(uomDto);
        UnitOfMeasure updatedUom = this.unitOfMeasureService.updateUnitOfMeasure(uom, id);
        return this.toUomDto.convert(updatedUom);
    }


    @Operation(summary = "This Operation Deletes Single Unit Of Measure From DB")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Deletes Single Unit From DB"),
                    @ApiResponse(responseCode = "404",
                            description = "Returns Error Message If Unit With The Passed ID Not Found In DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUnitOfMeasureById(@PathVariable Long id) {
        log.info("UnitOfMeasureController::deleteUnitOfMeasureById, ID passed = {}", id);
        this.unitOfMeasureService.deleteUnitOfMeasureById(id);
    }

}