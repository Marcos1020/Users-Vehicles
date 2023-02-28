package com.br.sanches.clientes.users.vehicle.repository;

import com.br.sanches.clientes.users.vehicle.entity.EntityCars;
import com.br.sanches.clientes.users.vehicle.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<EntityCars, Long> {
     Optional <EntityCars> findByIdUser(final UserEntity idUser);
     Optional <EntityCars> findByLicensePlate(final String licensePlate);
}
