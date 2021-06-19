package com.pu.georgidinov.pupracticumvoltwo.api.v1.converter;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ApplicationUserDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ShoppingListDto;
import com.pu.georgidinov.pupracticumvoltwo.domain.ApplicationUser;
import com.pu.georgidinov.pupracticumvoltwo.domain.ShoppingList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationUserToApplicationUserDto implements Converter<ApplicationUser, ApplicationUserDto> {

    private final ShoppingListToShoppingListDto toShoppingListDto;

    @Override
    public ApplicationUserDto convert(ApplicationUser applicationUser) {

        List<ShoppingList> lists = new ArrayList<>(applicationUser.getShoppingLists());
        List<ShoppingListDto> shoppingListDtoList = lists.stream().map(toShoppingListDto::convert).collect(Collectors.toList());

        return new ApplicationUserDto()
                .id(applicationUser.getId())
                .firstName(applicationUser.getFirstName())
                .lastName(applicationUser.getLastName())
                .shoppingLists(shoppingListDtoList);
    }

}