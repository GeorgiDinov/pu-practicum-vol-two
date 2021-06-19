package com.pu.georgidinov.pupracticumvoltwo.security.role;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.pu.georgidinov.pupracticumvoltwo.security.role.ApplicationUserPermission.*;

public enum ApplicationUserRole {

    USER(Sets.newHashSet(SHOPPING_LIST_READ, SHOPPING_LIST_WRITE, ITEM_READ, ITEM_WRITE, UNIT_OF_MEASURE_READ)),
    ADMIN(Sets.newHashSet(SHOPPING_LIST_READ, SHOPPING_LIST_WRITE, ITEM_READ, ITEM_WRITE, UNIT_OF_MEASURE_READ, UNIT_OF_MEASURE_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return this.permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }

}