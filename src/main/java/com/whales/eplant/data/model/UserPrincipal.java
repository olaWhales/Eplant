package com.whales.eplant.data.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record UserPrincipal(Users users) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // All users have the ROLE_USER authority
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // Add authorities for each vendor role the user has
        List<Vendor> vendors = users.getVendors();
        if (vendors != null) {
            for (Vendor vendor : vendors) {
                if (vendor.getRole() != null) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + vendor.getRole().name()));
                }
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}