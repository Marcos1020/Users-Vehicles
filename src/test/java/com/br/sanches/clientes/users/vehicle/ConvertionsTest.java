package com.br.sanches.clientes.users.vehicle;

import com.br.sanches.clientes.users.vehicle.controller.request.CarRequest;
import com.br.sanches.clientes.users.vehicle.controller.request.UserRequest;
import com.br.sanches.clientes.users.vehicle.entity.EntityCars;
import com.br.sanches.clientes.users.vehicle.entity.UserEntity;
import com.br.sanches.clientes.users.vehicle.repository.CarRepository;
import com.br.sanches.clientes.users.vehicle.repository.UserRepository;
import com.br.sanches.clientes.users.vehicle.utils.ConverterUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConvertionsTest {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    @Autowired
    public ConvertionsTest(UserRepository userRepository, CarRepository carRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    @NotNull
    public static UserRequest instantiatingANewUserAndVehicleForTheTest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Markin_Dev10");
        userRequest.setName("Marcos Vinicius Campos");
        userRequest.setCpf("93610007864");
        userRequest.setPassword("Br12-Je11-Rb87");

        CarRequest carRequest = new CarRequest();
        carRequest.setLicensePlate("olk1234");
        carRequest.setVehicleModel("honda civic lxs");
        carRequest.setCountry("Brasil");
        carRequest.setState("São Paulo");
        carRequest.setCity("Mirassol");
        userRequest.setCarRequest(carRequest);
        return userRequest;
    }

    public static UserRequest updateUserAndCarTest() {

        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Markin_Dev");
        userRequest.setName("Marcos Vinicius Campos");
        userRequest.setCpf("93610007864");
        userRequest.setPassword("Br12-Je11-Rb87");

        CarRequest carRequest = new CarRequest();
        carRequest.setLicensePlate("uir1234");
        carRequest.setVehicleModel("bmw m4");
        carRequest.setCountry("Brasil");
        carRequest.setState("São Paulo");
        carRequest.setCity("Mirassol");
        userRequest.setCarRequest(carRequest);
        return userRequest;
    }

    @NotNull
    public UserEntity SaveUserEntityAndCarEntityToTestUpdateUserANdVehicle() {
        UserEntity user = new UserEntity();
        user.setName("Marcos Vinicius");
        user.setUserName("NinjaFodão");
        user.setCpf("39610007864");
        user.setPassword("Br12-Je11-Rb87");
        user.setDateRegister(ConverterUtil.nowTime());
        userRepository.save(user);

        EntityCars entityCars = new EntityCars();
        entityCars.setVehicleModel("Ferrari f40");
        entityCars.setLicensePlate("wre0632");
        entityCars.setCountry("Brasil");
        entityCars.setState("São Paulo");
        entityCars.setCity("Mirassol");
        entityCars.setIdUser(user);
        carRepository.save(entityCars);
        return user;
    }
}