package com.br.sanches.clientes.users.vehicle.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class CarResponse {
    private Long idCar;
    private String licensePlate;
    private String vehicleModel;
    private String country;
    private String state;
    private String city;
}
