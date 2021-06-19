package com.pu.georgidinov.pupracticumvoltwo.api.v1.converter;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.UomDto;
import com.pu.georgidinov.pupracticumvoltwo.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UomDtoToUnitOfMeasureConverter implements Converter<UomDto, UnitOfMeasure> {

    @Synchronized
    @Override
    public UnitOfMeasure convert(UomDto uomDto) {
        return new UnitOfMeasure(uomDto.getId(), uomDto.getDescription());
    }

}