package com.pu.georgidinov.pupracticumvoltwo.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.AuthenticationRequestDto;
import com.pu.georgidinov.pupracticumvoltwo.security.auth.ApplicationUserDetails;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@Slf4j
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //== fields ==
    private final AuthenticationManager authenticationManager;
    private final JwtPropertyHolder jwtPropertyHolder;

    //== constructors ==
    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                   JwtPropertyHolder jwtPropertyHolder) {
        this.authenticationManager = authenticationManager;
        this.jwtPropertyHolder = jwtPropertyHolder;

        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationRequestDto authenticationRequest =
                    new ObjectMapper()
                            .readValue(request.getInputStream(), AuthenticationRequestDto.class);

            log.info("Login data received = {}", authenticationRequest);


            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                            authenticationRequest.getPassword());

            return this.authenticationManager.authenticate(authentication);

        } catch (IOException e) {
            throw new RuntimeException(e); //todo fix with custom exception
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        ApplicationUserDetails userDetails = (ApplicationUserDetails) authResult.getPrincipal();
        log.info("UserDetails id={} and username={} in successfulAuthMethod", userDetails.getUserId(), userDetails.getUsername());

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtPropertyHolder.getTokenExpirationAfterDays())))
                .signWith(jwtPropertyHolder.getSecretKey())
                .compact();

        response.addHeader(jwtPropertyHolder.getAuthorizationHeader(), jwtPropertyHolder.getTokenPrefix() + token);

        String issuedToken = jwtPropertyHolder.getTokenPrefix() + token;
        log.info("Token issued = {}", issuedToken);

        //uncomment to return the token on login
//        ReportDTO tokenReport = new ReportDTO(issuedToken);
//        String json = new ObjectMapper()
//                .writeValueAsString(ResponseEntity.of(Optional.of(tokenReport)).getBody());
//        log.info("The json = {}", json);
//        response.setContentType("application/json");
//        response.getWriter().write(json);
    }
}