package com.br.sanches.clientes.users.vehicle.JWTsecurity;

import com.br.sanches.clientes.users.vehicle.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class SecurityUserDatils implements UserDetails {

    private final Optional<UserEntity> user;

    public SecurityUserDatils(Optional<UserEntity> user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return user.orElse(new UserEntity()).getPassword();
    }

    @Override
    public String getUsername() {
        return user.orElse(new UserEntity()).getUserName();
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
