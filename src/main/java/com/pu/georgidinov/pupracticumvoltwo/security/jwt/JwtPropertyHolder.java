package com.pu.georgidinov.pupracticumvoltwo.security.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Getter
@Component
public class JwtPropertyHolder {

    //== fields ==
    private final String authorizationHeader = HttpHeaders.AUTHORIZATION;
    private final SecretKey secretKey;
    private final String tokenPrefix;
    private final Long tokenExpirationAfterDays;

    //== constructors ==
    @Autowired
    public JwtPropertyHolder(SecretKey secretKey,
                             @TokenPrefix String tokenPrefix,
                             @TokenExpirationAfterDays Long tokenExpirationAfterDays) {
        this.secretKey = secretKey;
        this.tokenPrefix = tokenPrefix;
        this.tokenExpirationAfterDays = tokenExpirationAfterDays;
    }

}