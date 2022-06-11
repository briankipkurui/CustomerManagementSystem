package com.example.SpringSecurity.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.SpringSecurity.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    CUSTOMER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ,COURSE_WRITE,CUSTOMER_READ,CUSTOMER_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(COURSE_READ,CUSTOMER_READ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return  permissions;


    }

    }
