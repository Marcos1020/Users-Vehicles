package com.br.sanches.clientes.users.vehicle.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    private String name;

    private String cpf;

    private String userName;

    private String password;

    private UpdateLocalizationVehicle localizationVehicle;

}
