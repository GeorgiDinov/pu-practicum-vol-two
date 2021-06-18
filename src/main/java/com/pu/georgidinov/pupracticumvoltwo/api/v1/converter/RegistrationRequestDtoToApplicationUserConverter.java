package com.pu.georgidinov.pupracticumvoltwo.api.v1.converter;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.RegistrationRequestDto;
import com.pu.georgidinov.pupracticumvoltwo.domain.ApplicationUser;
import com.pu.georgidinov.pupracticumvoltwo.domain.ApplicationUserCredentials;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceAlreadyExistsException;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceNotFoundException;
import com.pu.georgidinov.pupracticumvoltwo.repository.ApplicationUserCredentialsRepository;
import com.pu.georgidinov.pupracticumvoltwo.security.role.ApplicationUserRole;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RegistrationRequestDtoToApplicationUserConverter implements Converter<RegistrationRequestDto, ApplicationUser> {


    private final ApplicationUserCredentialsRepository credentialsRepository;

    @Synchronized
    @Override
    public ApplicationUser convert(RegistrationRequestDto dto) {
        this.validateCredentials(dto);

        ApplicationUserCredentials credentials = new ApplicationUserCredentials();
        credentials.email(dto.getEmail()).password(dto.getPassword()).userRole(ApplicationUserRole.USER);

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.firstName(dto.getFirstName()).lastName(dto.getLastName()).credentials(credentials);


        return applicationUser;
    }


    private void validateCredentials(RegistrationRequestDto dto) {
        if (dto == null || dto.getEmail() == null || dto.getPassword() == null) {
            throw new ResourceNotFoundException("Invalid Registration Request");
        }

        if (dto.getEmail() != null) {
            if (this.credentialsRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new ResourceAlreadyExistsException("Email " + dto.getEmail() + " already exists!");
            }
        }
    }

}