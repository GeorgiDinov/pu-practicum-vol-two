package com.pu.georgidinov.pupracticumvoltwo.api.v1.converter;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ItemDto;
import com.pu.georgidinov.pupracticumvoltwo.domain.Item;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemToItemDtoConverter implements Converter<Item, ItemDto> {

    @Synchronized
    @Override
    public ItemDto convert(Item item) {
        return new ItemDto()
                .id(item.getId())
                .name(item.getName())
                .quantity(item.getQuantity())
                .units(item.getUnitOfMeasure().getDescription());
    }

}