package com.br.sanches.clientes.users.vehicle.service;

import com.br.sanches.clientes.users.vehicle.client.EmailClient;
import com.br.sanches.clientes.users.vehicle.controller.request.LoginRequest;
import com.br.sanches.clientes.users.vehicle.controller.request.UpdateLicensePlateOrModelVehicleRequest;
import com.br.sanches.clientes.users.vehicle.controller.request.UpdateUserRequest;
import com.br.sanches.clientes.users.vehicle.controller.request.UserRequest;
import com.br.sanches.clientes.users.vehicle.controller.response.UserResponse;
import com.br.sanches.clientes.users.vehicle.convertions.Convertions;
import com.br.sanches.clientes.users.vehicle.entity.EntityCars;
import com.br.sanches.clientes.users.vehicle.entity.UserEntity;
import com.br.sanches.clientes.users.vehicle.exception.BadRequestException;
import com.br.sanches.clientes.users.vehicle.exception.ObjectAlreadyExists;
import com.br.sanches.clientes.users.vehicle.exception.PreconditionFailedException;
import com.br.sanches.clientes.users.vehicle.repository.CarRepository;
import com.br.sanches.clientes.users.vehicle.repository.UserRepository;
import com.br.sanches.clientes.users.vehicle.utils.Constants;
import com.br.sanches.clientes.users.vehicle.utils.ConverterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@Component
public class UserService {

    private UserRepository userRepository;
    private CarRepository carRepository;
    private Convertions convertions;
    private final PasswordEncoder encoder;
    private final EmailClient emailClient;

    @Autowired
    public UserService(UserRepository userRepository, CarRepository carRepository,
                       Convertions convertions, PasswordEncoder encoder, EmailClient emailClient) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.convertions = convertions;
        this.encoder = encoder;
        this.emailClient = emailClient;
    }

    public UserResponse newUser(UserRequest userRequest) throws ObjectAlreadyExists {

        log.info(Constants.REGISTER_NEW_USER_AND_BEHICLE);

        Optional<UserEntity> userEntity = this.userRepository.findByUserName(userRequest.getUserName());
        if (userEntity.isPresent()) {
            log.info(Constants.USER_NAME_EXISTING);
            throw new ObjectAlreadyExists(Constants.USER_NAME_EXISTING);

        } else {
            Optional<EntityCars> entityCars = this.carRepository.findByLicensePlate(userRequest.getCarRequest().getLicensePlate());
            if (entityCars.isPresent()) {
                log.info(Constants.INVALID_LICENSE_PLATE);
                throw new ObjectAlreadyExists(Constants.INVALID_LICENSE_PLATE);
            }
        }

        UserEntity user = UserEntity.builder().build();
        convertions.convertUserRequestToEntity(userRequest, user);
        UserEntity entityUser = this.userRepository.save(user);

        EntityCars carsEntity = EntityCars.builder().build();
        convertions.convertCarRequestToEntity(userRequest.getCarRequest(), carsEntity);
        carsEntity.setUserEntity(entityUser);
        EntityCars entitySave = this.carRepository.save(carsEntity);

        convertions.SendEmailToRegisteredUserVahicleAndVehicle(userRequest.getEmailRequest(), emailClient, entityUser);

        UserResponse userResponse = convertions.convertEntityToResponse(entityUser);
        userResponse.setCarResponse(convertions.convertEntityToResponsetoCar(entitySave));

        return userResponse;
    }

    public UserResponse searchByName(final String name) throws PreconditionFailedException {

        UserEntity userEntity = this.userRepository.findByName(name);
        EntityCars entityCars = this.carRepository.findByUserEntity(userEntity).orElse(null);

        if (Objects.isNull(userEntity)) {
            log.info(Constants.NAME_NOT_FOUND);
            throw new PreconditionFailedException(Constants.NAME_NOT_FOUND);

        } else if (!name.equalsIgnoreCase(entityCars.getUserEntity().getName())) {
            log.info(Constants.INCORRECT_NAME);
            throw new PreconditionFailedException(Constants.INCORRECT_NAME);
        }

        UserResponse userResponse = convertions.convertEntityToResponse(userEntity);
        userResponse.setCarResponse(convertions.convertEntityToResponsetoCar(entityCars));

        return userResponse;
    }

    public UserResponse updateUserAndCar(final Long idUser, final UpdateUserRequest updateUserRequest) throws BadRequestException {

        final UserEntity userEntity = this.userRepository.findById(idUser).orElse(null);
        final EntityCars entityCars = this.carRepository.findByUserEntity(userEntity).orElse(null);

        if (Objects.isNull(userEntity)) {
            log.info(Constants.ID_NOT_FOUND);
            throw new BadRequestException(Constants.ID_NOT_FOUND);

        } else if (!idUser.equals(entityCars.getUserEntity().getIdUser())) {
            log.info(Constants.ID_NOT_FOUND);
            throw new BadRequestException(Constants.ID_NOT_FOUND);
        }

        convertions.convertUpdateUserRequest(updateUserRequest, userEntity);
        UserEntity entityUser = this.userRepository.save(userEntity);

        convertions.convertUpdateCarRequest(updateUserRequest.getLocalizationVehicle(), entityCars);
        EntityCars carsEntity = this.carRepository.save(entityCars);

        UserResponse userResponse = convertions.convertEntityToResponse(entityUser);
        userResponse.setCarResponse(convertions.convertEntityToResponsetoCar(carsEntity));

        return userResponse;
    }

    public void delete(final Long idUser) throws PreconditionFailedException {

        final UserEntity userEntity = this.userRepository.findById(idUser).orElse(null);

        if (Objects.isNull(userEntity)) {
            log.info(Constants.ID_NOT_FOUND);
            throw new PreconditionFailedException(Constants.ID_NOT_FOUND);

        } else if (!idUser.equals(userEntity.getIdUser())) {
            log.info(Constants.ID_NOT_FOUND);
            throw new PreconditionFailedException(Constants.ID_NOT_FOUND);
        }

        final Optional<EntityCars> cars = this.carRepository.findByUserEntity(userEntity);
        this.carRepository.deleteById(cars.get().getIdCar());
        this.userRepository.delete(userEntity);
    }

    public void userLogin(LoginRequest loginRequest) throws BadRequestException {

        log.info(Constants.INITIALIZER_LOGIN_USER);

        final Optional<UserEntity> userEntity = this.userRepository.findByUserName(loginRequest.getUserName());

        if (!userEntity.isPresent()) {
            log.info(Constants.INVALID_USER);
            throw new PreconditionFailedException(Constants.INVALID_USER);

        } else if (!encoder.matches(loginRequest.getPassword(), userEntity.get().getPassword())) {
            log.info(Constants.INAVALID_PASSWORD);
            throw new PreconditionFailedException(Constants.INAVALID_PASSWORD);
        }
    }

    public EntityCars searchByLicensePlate(final String licensePlate) throws BadRequestException {

        Optional<EntityCars> carsEntity = carRepository.findByLicensePlate(licensePlate);

        if (!carsEntity.isPresent()) {
            log.info(Constants.SEARCH_BY_LICENSE_PLATE_FAILED);
            throw new BadRequestException(Constants.SEARCH_BY_LICENSE_PLATE_FAILED);
        }

        EntityCars entityCars = carsEntity.get();
        if (!licensePlate.equalsIgnoreCase(entityCars.getLicensePlate())) {
            log.info(Constants.SEARCH_BY_LICENSE_PLATE_FAILED);
            throw new BadRequestException(Constants.SEARCH_BY_LICENSE_PLATE_FAILED);
        }

        return entityCars;
    }

    public UserResponse updateLicensePlate(final Long idUser, final UpdateLicensePlateOrModelVehicleRequest request) throws BadRequestException {

        final UserEntity userEntity = this.userRepository.findById(idUser).orElse(null);
        final EntityCars entityCars = this.carRepository.findByUserEntity(userEntity).orElse(null);

        if (!entityCars.getUserEntity().getIdUser().equals(idUser)) {
            log.info(Constants.ID_NOT_FOUND);
            throw new BadRequestException(Constants.ID_NOT_FOUND);

        } else if (request.getLicensePlate().equalsIgnoreCase(entityCars.getLicensePlate())) {
            log.info(Constants.VEHICLE_PLATE_ALREADY_REGISTERED);
            throw new BadRequestException(Constants.VEHICLE_PLATE_ALREADY_REGISTERED);
        }
        try {
            entityCars.setLicensePlate(request.getLicensePlate().toUpperCase());
            entityCars.setDateUpdate(ConverterUtil.nowTime());

        } catch (BadRequestException exception) {
            log.info(Constants.MANDATORY_FIELDS);
            throw new BadRequestException(Constants.MANDATORY_FIELDS);
        }
        EntityCars carsEntity = this.carRepository.save(entityCars);

        UserResponse userResponse = convertions.convertEntityToResponse(userEntity);
        userResponse.setCarResponse(convertions.convertEntityToResponsetoCar(carsEntity));

        return userResponse;
    }

    public EntityCars updateVehicleModel(final String licensePlate, final UpdateLicensePlateOrModelVehicleRequest request) throws BadRequestException{

        Optional<EntityCars> entityCars = carRepository.findByLicensePlate(licensePlate);

        if (!entityCars.isPresent()) {
            log.info(Constants.INVALID_VEHICLE_LICENSE_PLATE);
            throw new BadRequestException(Constants.INVALID_VEHICLE_LICENSE_PLATE);

        } else if (!entityCars.get().getLicensePlate().equalsIgnoreCase(licensePlate)) {
            log.info(Constants.SEARCH_BY_LICENSE_PLATE_FAILED);
            throw new BadRequestException(Constants.SEARCH_BY_LICENSE_PLATE_FAILED);
        }

        try {
            entityCars.get().setVehicleModel(request.getVehicleModel().toUpperCase());
            entityCars.get().setDateUpdate(ConverterUtil.nowTime());

        } catch (BadRequestException exception) {
            log.info(Constants.MANDATORY_FIELDS);
            throw new BadRequestException(Constants.MANDATORY_FIELDS);
        }

        EntityCars carsEntity = this.carRepository.save(entityCars.orElseThrow(()
                -> new BadRequestException(Constants.INVALID_ENTITY)));
        return carsEntity;
    }
}
