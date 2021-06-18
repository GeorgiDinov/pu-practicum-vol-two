package com.pu.georgidinov.pupracticumvoltwo.api.v1.converter;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ShoppingListDto;
import com.pu.georgidinov.pupracticumvoltwo.domain.ShoppingList;
import com.pu.georgidinov.pupracticumvoltwo.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ShoppingListDtoToShoppingList implements Converter<ShoppingListDto, ShoppingList> {

    private final ApplicationUserRepository userRepository;

    @Override
    public ShoppingList convert(ShoppingListDto dto) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList
                .id(dto.getId())
                .title(dto.getTitle());
        return shoppingList;
    }

//    private String extractUser(ShoppingListDto dto) {
//        ApplicationUser user = new ApplicationUser();
//        if (dto != null && dto.getUserId() != null) {
//            Long id = dto.getUserId();
//            user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with ID = '" + id + "' NOT FOUND"));
//        }
//        return user;
//    }

}