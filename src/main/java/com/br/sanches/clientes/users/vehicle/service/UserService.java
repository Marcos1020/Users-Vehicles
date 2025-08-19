package com.br.sanches.clientes.users.vehicle.service;

import com.br.sanches.clientes.users.vehicle.controller.request.LoginRequest;
import com.br.sanches.clientes.users.vehicle.controller.request.UserRequest;
import com.br.sanches.clientes.users.vehicle.controller.response.UserResponse;
import com.br.sanches.clientes.users.vehicle.convertions.Convertions;
import com.br.sanches.clientes.users.vehicle.entity.EntityCars;
import com.br.sanches.clientes.users.vehicle.entity.UserEntity;
import com.br.sanches.clientes.users.vehicle.exception.ObjectAlreadyExists;
import com.br.sanches.clientes.users.vehicle.exception.PreconditionFailedException;
import com.br.sanches.clientes.users.vehicle.repository.CarRepository;
import com.br.sanches.clientes.users.vehicle.repository.UserRepository;
import com.br.sanches.clientes.users.vehicle.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private CarRepository carRepository;
    private Convertions convertions;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, CarRepository carRepository, Convertions convertions, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.convertions = convertions;
        this.encoder = encoder;
    }

    public UserResponse newUser(UserRequest userRequest )throws ObjectAlreadyExists {

        log.info(Constants.REGISTER_NEW_USER);

        Optional<UserEntity> userEntity = this.userRepository.findByUserName(userRequest.getUserName());
        if (userEntity.isPresent()) {
            log.info(Constants.USER_NAME_EXISTING);
            throw new ObjectAlreadyExists(Constants.USER_NAME_EXISTING);
        } else{
            Optional<EntityCars> entityCars = this.carRepository.findByLicensePlate(userRequest.getCarRequest().getLicensePlate());
            if (entityCars.isPresent()) {
                log.info(Constants.INVALID_LICENSE_PLATE);
                throw new ObjectAlreadyExists(Constants.INVALID_LICENSE_PLATE);
            }
        }

        UserEntity user = new UserEntity();
        convertions.convertUserRequestToEntity(userRequest, user);
        this.userRepository.save(user);

        EntityCars carsEntity = new EntityCars();
        convertions.convertCarRequestToEntity(userRequest.getCarRequest(), carsEntity);
        carsEntity.setIdUser(user);
        EntityCars entitySave = this.carRepository.save(carsEntity);

        UserResponse userResponse = convertions.convertEntityToResponse(user);
        userResponse.setCarResponse(convertions.convertEntityToResponsetoCar(entitySave));

        return userResponse;
    }

    public UserResponse searchById(Long idUser) throws PreconditionFailedException {

        final UserEntity userEntity = this.userRepository.findById(idUser).orElse(null);
        if (Objects.isNull(userEntity)) {
            log.info(Constants.ID_NAO_ENCONTRADO);
            throw new PreconditionFailedException(Constants.ID_NAO_ENCONTRADO);
        }
        final EntityCars entityCars = this.carRepository.findByIdUser(userEntity).orElse(null);

        UserResponse userResponse = convertions.convertEntityToResponse(userEntity);
        userResponse.setCarResponse(convertions.convertEntityToResponsetoCar(entityCars));

        return userResponse;
    }

    public UserResponse updateUserAndCar(final Long idUser, final UserRequest userRequest) throws PreconditionFailedException {

        final UserEntity user = this.userRepository.findById(idUser).orElse(null);
        if (Objects.isNull(user)) {
            log.info(Constants.ID_NAO_ENCONTRADO);
            throw new PreconditionFailedException(Constants.ID_NAO_ENCONTRADO);
        }
        final EntityCars cars = this.carRepository.findByIdUser(user).orElse(null);
        if (Objects.isNull(cars)) {
            log.info(Constants.ID_NAO_ENCONTRADO);
            throw new PreconditionFailedException(Constants.ID_NAO_ENCONTRADO);
        }

        convertions.convertUpdateUserRequest(userRequest, user);
        UserEntity entityUser = this.userRepository.save(user);

        convertions.convertUpdateCarRequest(userRequest.getCarRequest(), cars);
        EntityCars carsEntity = this.carRepository.save(cars);

        UserResponse userResponse = convertions.convertEntityToResponse(entityUser);
        userResponse.setCarResponse(convertions.convertEntityToResponsetoCar(carsEntity));

        return userResponse;
    }

    public void delete(final Long idUser) throws PreconditionFailedException {

        UserEntity userEntity = this.userRepository.findById(idUser).orElse(null);

        if (Objects.isNull(userEntity)) {
            log.info(Constants.ID_NAO_ENCONTRADO);
            throw new PreconditionFailedException(Constants.ID_NAO_ENCONTRADO);
        }

        Optional<EntityCars> cars = this.carRepository.findByIdUser(userEntity);
        cars.ifPresent(entity -> this.carRepository.deleteById(entity.getIdCar()));
        this.userRepository.delete(userEntity);
    }

    public void userLogin(LoginRequest loginRequest) throws PreconditionFailedException {

        log.info(Constants.INITIALIZER_LOGIN_USER);

        final Optional<UserEntity> userEntity = this.userRepository.findByUserName(loginRequest.getUserName());

        if (!userEntity.isPresent()) {
            log.info(Constants.USER_NOT_FOUND);
            throw new PreconditionFailedException(Constants.USER_NOT_FOUND);
        } else {
            if (!encoder.matches(loginRequest.getPassword(), userEntity.get().getPassword())) {
                log.info(Constants.INAVALID_PASSWORD);
                throw new PreconditionFailedException(Constants.INAVALID_PASSWORD);
            }
        }
    }
}
