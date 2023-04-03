package com.br.sanches.clientes.users.vehicle.controller.response;

import com.br.sanches.clientes.users.vehicle.utils.ConverterUtil;
import com.br.sanches.clientes.users.vehicle.utils.DateAndTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long idUser;

    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String cpf;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonDeserialize(using = DateAndTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ConverterUtil.FORMATO_DATA)
    private Date dateRegister;

    @JsonDeserialize(using = DateAndTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ConverterUtil.FORMATO_DATA)
    private Date dateUpdate;

    private CarResponse carResponse;
}
