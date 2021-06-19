package com.pu.georgidinov.pupracticumvoltwo.security.config;

import com.pu.georgidinov.pupracticumvoltwo.security.auth.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticationProviderConfig {

    //== fields ==
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserDetailsService applicationUserDetailsService;

    //== constructors ==
    @Autowired
    public AuthenticationProviderConfig(PasswordEncoder passwordEncoder,
                                        ApplicationUserDetailsService applicationUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserDetailsService = applicationUserDetailsService;
    }

    //== bean methods ==
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserDetailsService);
        return provider;
    }
}