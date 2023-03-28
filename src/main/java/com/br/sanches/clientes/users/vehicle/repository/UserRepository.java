package com.br.sanches.clientes.users.vehicle.repository;

import com.br.sanches.clientes.users.vehicle.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(final String userName);

    UserEntity findByName(final String name);

}
