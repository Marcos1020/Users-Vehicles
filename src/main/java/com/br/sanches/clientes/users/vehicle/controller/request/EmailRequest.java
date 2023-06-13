package com.br.sanches.clientes.users.vehicle.controller.request;

import com.br.sanches.clientes.users.vehicle.statusEmail.StatusEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class EmailRequest {

    @NotBlank
    @Email
    private String emailTo;

    @NotBlank
    private StatusEmail statusEmail;
}
