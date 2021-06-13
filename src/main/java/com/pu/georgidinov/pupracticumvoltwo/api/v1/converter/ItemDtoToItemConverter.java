package com.pu.georgidinov.pupracticumvoltwo.api.v1.converter;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ItemDto;
import com.pu.georgidinov.pupracticumvoltwo.domain.Item;
import com.pu.georgidinov.pupracticumvoltwo.repository.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemDtoToItemConverter implements Converter<ItemDto, Item> {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Override
    public Item convert(ItemDto itemDto) {
        Item item = new Item().id(itemDto.getId()).name(itemDto.getName()).quantity(itemDto.getQuantity());

        this.unitOfMeasureRepository.findByDescription(itemDto.getUnits()).ifPresent(item::unitOfMeasure);

        return item;
    }
}