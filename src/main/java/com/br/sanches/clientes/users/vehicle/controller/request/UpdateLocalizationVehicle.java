package com.br.sanches.clientes.users.vehicle.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLocalizationVehicle {

    private String country;

    private String state;

    private String city;
}
