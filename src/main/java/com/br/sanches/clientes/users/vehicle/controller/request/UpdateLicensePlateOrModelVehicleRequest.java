package com.br.sanches.clientes.users.vehicle.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLicensePlateOrModelVehicleRequest {

    private String licensePlate;

    private String vehicleModel;

}
