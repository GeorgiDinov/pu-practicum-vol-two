package com.pu.georgidinov.pupracticumvoltwo.controller.v1;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ApplicationUserToApplicationUserDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.RegistrationRequestDtoToApplicationUserConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ApplicationUserDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.RegistrationRequestDto;
import com.pu.georgidinov.pupracticumvoltwo.domain.ApplicationUser;
import com.pu.georgidinov.pupracticumvoltwo.service.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("assistant/api/v1/user")
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;
    private final RegistrationRequestDtoToApplicationUserConverter toUser;
    private final ApplicationUserToApplicationUserDto toRegResponse;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationUserDto register(@RequestBody RegistrationRequestDto registrationRequestDto) {
        log.info("ApplicationUserController::register, DTO passed = {}", registrationRequestDto);
        ApplicationUser user = this.toUser.convert(registrationRequestDto);
        ApplicationUser newUser = this.applicationUserService.saveApplicationUser(user);
        return this.toRegResponse.convert(newUser);
    }


}