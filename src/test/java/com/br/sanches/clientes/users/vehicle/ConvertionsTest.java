package com.br.sanches.clientes.users.vehicle;

import com.br.sanches.clientes.users.vehicle.controller.request.CarRequest;
import com.br.sanches.clientes.users.vehicle.controller.request.UserRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConvertionsTest {

    @NotNull
    public static UserRequest instantiatingANewUserAndVehicleForTheTest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Markin_Dev20");
        userRequest.setName("Marcos Vinicius Campos");
        userRequest.setCpf("93610007864");
        userRequest.setPassword("Br12-Je11-Rb87");

        CarRequest carRequest = new CarRequest();
        carRequest.setLicensePlate("efh2412");
        carRequest.setVehicleModel("honda civic lxs");
        carRequest.setCountry("Brasil");
        carRequest.setState("São Paulo");
        carRequest.setCity("Mirassol");
        userRequest.setCarRequest(carRequest);
        return userRequest;
    }
}
