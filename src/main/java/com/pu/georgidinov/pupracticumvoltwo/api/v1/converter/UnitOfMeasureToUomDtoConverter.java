package com.pu.georgidinov.pupracticumvoltwo.api.v1.converter;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.UomDto;
import com.pu.georgidinov.pupracticumvoltwo.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUomDtoConverter implements Converter<UnitOfMeasure, UomDto> {

    @Synchronized
    @Override
    public UomDto convert(UnitOfMeasure uom) {
        return new UomDto(uom.getId(), uom.getDescription());
    }

}