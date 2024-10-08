package com.milestone4.ticketplatform.security;

import com.milestone4.ticketplatform.models.Operator;
import com.milestone4.ticketplatform.models.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DatabaseUserDetails implements UserDetails {

    private final Long id;
    private final String email;
    private final String username;
    private final String password;
    private final boolean active;
    private final Set<GrantedAuthority> authorities;

    public DatabaseUserDetails(Operator operator){
        this.id=operator.getId();
        this.email=operator.getEmail();
        this.username=operator.getUsername();
        this.password=operator.getPassword();
        this.active = operator.isActive();

        //Set Authorities
        authorities = new HashSet<>();
        for (Role role : operator.getRoles()){
            authorities.add((new SimpleGrantedAuthority(role.getName())));
        }
    }


    public Long getId() {
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean isActive() {
        return active;
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
