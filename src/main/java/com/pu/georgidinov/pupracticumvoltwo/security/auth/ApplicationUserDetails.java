package com.pu.georgidinov.pupracticumvoltwo.security.auth;

import com.pu.georgidinov.pupracticumvoltwo.domain.ApplicationUserCredentials;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class ApplicationUserDetails implements UserDetails {

    //== constants ==
    private static final boolean TRUE = true;

    //== fields ==
    //---------------------------------------------
    private boolean isAccountNonExpired = TRUE;      //todo figure out how to implement if needed
    private boolean isAccountNonLocked = TRUE;       //todo figure out how to implement if needed
    private boolean isCredentialsNonExpired = TRUE;  //todo figure out how to implement if needed
    private boolean isEnabled = TRUE;                //todo figure out how to implement if needed
    //---------------------------------------------

    private ApplicationUserCredentials applicationUserCredentials;
    private Long userId;
    private String username;
    private String password;
    private Set<? extends GrantedAuthority> grantedAuthorities;

    //== constructors ==
    public ApplicationUserDetails(ApplicationUserCredentials applicationUserCredentials) {
        this.applicationUserCredentials = applicationUserCredentials;
        this.userId = this.applicationUserCredentials.getApplicationUser().getId();
        this.username = this.applicationUserCredentials.getEmail();
        this.password = this.applicationUserCredentials.getPassword();
        this.grantedAuthorities = applicationUserCredentials.getUserRole().getGrantedAuthorities();
    }


    //== public methods ==
    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}