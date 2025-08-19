package com.br.sanches.clientes.users.vehicle.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class UserRequest {

    @NotEmpty(message = "O campo nome é obrigatório")
    private String name;
    @NotEmpty(message = "O campo cpf é obrigatório")
    @Size(min = 11, max = 12, message = "CPF deve conter no minimo 11 caracters")
    private String cpf;
    private String userName;
    private String password;
    private CarRequest carRequest;

}
