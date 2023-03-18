package com.br.sanches.clientes.users.vehicle.convertions;

import com.br.sanches.clientes.users.vehicle.controller.request.CarRequest;
import com.br.sanches.clientes.users.vehicle.controller.request.UserRequest;
import com.br.sanches.clientes.users.vehicle.controller.response.CarResponse;
import com.br.sanches.clientes.users.vehicle.controller.response.UserResponse;
import com.br.sanches.clientes.users.vehicle.entity.EntityCars;
import com.br.sanches.clientes.users.vehicle.entity.UserEntity;
import com.br.sanches.clientes.users.vehicle.exception.PreconditionFailedException;
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
        UserResponse userResponse = new UserResponse();
        userResponse.setIdUser(user.getIdUser());
        userResponse.setCpf(user.getCpf());
        userResponse.setName(user.getName());
        userResponse.setUserName(user.getUserName());
        userResponse.setPassword(user.getPassword());
        userResponse.setDateRegister(user.getDateRegister());
        userResponse.setDateUpdate(user.getDateUpdate());
        return userResponse;
    }
    public CarResponse convertEntityToResponsetoCar(EntityCars cars) {
        CarResponse carResponse = new CarResponse();
        carResponse.setIdCar(cars.getIdCar());
        carResponse.setLicensePlate(cars.getLicensePlate().toUpperCase());
        carResponse.setVehicleModel(cars.getVehicleModel().toUpperCase());
        carResponse.setCountry(cars.getCountry());
        carResponse.setState(cars.getState());
        carResponse.setCity(cars.getCity());
        return carResponse;
    }
    public void convertUpdateCarRequest(CarRequest carRequest, EntityCars entityCars)throws PreconditionFailedException {
       try {
           entityCars.setLicensePlate(carRequest.getLicensePlate().toUpperCase());
           entityCars.setVehicleModel(carRequest.getVehicleModel().toUpperCase());
           entityCars.setCountry(carRequest.getCountry());
           entityCars.setState(carRequest.getState());
           entityCars.setCity(carRequest.getCity());
       }catch (PreconditionFailedException exception){
           log.info(Constants.CAMPOS_OBRIGATORIOS);
           throw new PreconditionFailedException(Constants.CAMPOS_OBRIGATORIOS);
       }
    }
    public void convertUpdateUserRequest(UserRequest userRequest, UserEntity user) throws PreconditionFailedException {
        try {
            user.setName(userRequest.getName());
            user.setCpf(encoder.encode(userRequest.getCpf()));
            user.setUserName(userRequest.getUserName());
            user.setPassword(encoder.encode(userRequest.getPassword()));
            user.setDateUpdate(ConverterUtil.nowTime());
        }catch (Exception exception){
            log.info(Constants.CAMPOS_OBRIGATORIOS);
            throw new PreconditionFailedException(Constants.CAMPOS_OBRIGATORIOS);
        }
    }

}
