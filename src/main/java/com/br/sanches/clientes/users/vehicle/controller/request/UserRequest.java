package com.br.sanches.clientes.users.vehicle.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class UserRequest {


    private String name;

    private String email;

    private String cpf;

    private String userName;

    private String password;

    private CarRequest carRequest;

    private EmailRequest emailRequest;

}
