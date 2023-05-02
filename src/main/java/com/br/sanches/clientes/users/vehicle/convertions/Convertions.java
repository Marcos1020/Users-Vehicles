package com.br.sanches.clientes.users.vehicle.convertions;

import com.br.sanches.clientes.users.vehicle.client.EmailClient;
import com.br.sanches.clientes.users.vehicle.controller.request.*;
import com.br.sanches.clientes.users.vehicle.controller.response.CarResponse;
import com.br.sanches.clientes.users.vehicle.controller.response.UserResponse;
import com.br.sanches.clientes.users.vehicle.entity.EntityCars;
import com.br.sanches.clientes.users.vehicle.entity.UserEntity;
import com.br.sanches.clientes.users.vehicle.exception.BadRequestException;
import com.br.sanches.clientes.users.vehicle.statusEmail.StatusEmail;
import com.br.sanches.clientes.users.vehicle.utils.Constants;
import com.br.sanches.clientes.users.vehicle.utils.ConverterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class Convertions {
    private final PasswordEncoder encoder;

    @Autowired
    public Convertions(PasswordEncoder encoder){
        this.encoder = encoder;

    }
    public void convertUserRequestToEntity(UserRequest userRequest, UserEntity user) {
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setCpf(encoder.encode(userRequest.getCpf()));
        user.setUserName(userRequest.getUserName());
        user.setPassword(encoder.encode(userRequest.getPassword()));
        user.setDateRegister(ConverterUtil.nowTime());
    }
    public static void convertCarRequestToEntity(CarRequest carRequest, EntityCars entityCars) {
        entityCars.setLicensePlate(carRequest.getLicensePlate().toUpperCase());
        entityCars.setVehicleModel(carRequest.getVehicleModel().toUpperCase());
        entityCars.setCountry(carRequest.getCountry());
        entityCars.setState(carRequest.getState());
        entityCars.setCity(carRequest.getCity());
    }
    public UserResponse convertEntityToResponse(UserEntity user) {
        UserResponse userResponse = UserResponse.builder().build();
        userResponse.setIdUser(user.getIdUser());
        userResponse.setCpf(user.getCpf());
        userResponse.setName(user.getName());
        userResponse.setUserName(user.getUserName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setDateRegister(user.getDateRegister());
        userResponse.setDateUpdate(user.getDateUpdate());
        return userResponse;
    }
    public CarResponse convertEntityToResponsetoCar(EntityCars cars) {
        CarResponse carResponse = CarResponse.builder().build();
        carResponse.setIdCar(cars.getIdCar());
        carResponse.setLicensePlate(cars.getLicensePlate().toUpperCase());
        carResponse.setVehicleModel(cars.getVehicleModel().toUpperCase());
        carResponse.setCountry(cars.getCountry());
        carResponse.setState(cars.getState());
        carResponse.setCity(cars.getCity());
        return carResponse;
    }
    public void convertUpdateCarRequest(UpdateLocalizationVehicle updateCarRequest, EntityCars entityCars)throws BadRequestException {
       try {
           entityCars.setCountry(updateCarRequest.getCountry());
           entityCars.setState(updateCarRequest.getState());
           entityCars.setCity(updateCarRequest.getCity());
       }catch (BadRequestException exception){
           log.info(Constants.MANDATORY_FIELDS);
           throw new BadRequestException(Constants.MANDATORY_FIELDS);
       }
    }
    public void convertUpdateUserRequest(UpdateUserRequest updateUserRequest, UserEntity user) throws BadRequestException {
        try {
            user.setName(updateUserRequest.getName());
            user.setCpf(encoder.encode(updateUserRequest.getCpf()));
            user.setUserName(updateUserRequest.getUserName());
            user.setPassword(encoder.encode(updateUserRequest.getPassword()));
            user.setDateUpdate(ConverterUtil.nowTime());
        }catch (BadRequestException exception){
            log.info(Constants.MANDATORY_FIELDS);
            throw new BadRequestException(Constants.MANDATORY_FIELDS);
        }
    }

    public void SendEmailToRegisteredUserVahicleAndVehicle(EmailRequest emailRequest, EmailClient emailClient, UserEntity entityUser){
        emailRequest.setEmailTo(entityUser.getEmail());
        emailRequest.setSubject(entityUser.getName());
        emailRequest.setStatusEmail(StatusEmail.SEND);
        emailClient.sendEmail(emailRequest);
    }
}