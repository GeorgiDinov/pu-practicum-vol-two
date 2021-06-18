package com.pu.georgidinov.pupracticumvoltwo.api.v1.converter;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ApplicationUserDto;
import com.pu.georgidinov.pupracticumvoltwo.domain.ApplicationUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationUserToApplicationUserDto implements Converter<ApplicationUser, ApplicationUserDto> {

    private final ShoppingListToShoppingListDto toShoppingListDto;

    @Override
    public ApplicationUserDto convert(ApplicationUser applicationUser) {
        return new ApplicationUserDto()
                .id(applicationUser.getId())
                .firstName(applicationUser.getFirstName())
                .lastName(applicationUser.getLastName());
    }

}