package com.br.sanches.clientes.users.vehicle.JWTsecurity;

import com.br.sanches.clientes.users.vehicle.entity.UserEntity;
import com.br.sanches.clientes.users.vehicle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class SecurityUserDetailServiceImpl implements UserDetailsService {

    private final UserRepository repository;
    @Autowired
    public SecurityUserDetailServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> user = repository.findByUserName(username);
        if(StringUtils.isEmpty(user))
            throw new UsernameNotFoundException("Usuario [" + username + "] não encontrado");

        return new SecurityUserDatils(user);
    }
}
