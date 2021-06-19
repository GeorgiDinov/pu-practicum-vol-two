package com.pu.georgidinov.pupracticumvoltwo.security.auth;

import com.pu.georgidinov.pupracticumvoltwo.domain.ApplicationUserCredentials;
import com.pu.georgidinov.pupracticumvoltwo.repository.ApplicationUserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final ApplicationUserCredentialsRepository credentialsRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("SchoolUserDetailsService::loadUserByUsername -> username passed = {}", username);
        Optional<ApplicationUserCredentials> optionalSchoolUserCredentials =
                this.credentialsRepository.findByEmail(username);

        ApplicationUserCredentials userCredentials =
                optionalSchoolUserCredentials
                        .orElseThrow(() -> new UsernameNotFoundException(username + " NOT FOUND"));

        return new ApplicationUserDetails(userCredentials);
    }

}