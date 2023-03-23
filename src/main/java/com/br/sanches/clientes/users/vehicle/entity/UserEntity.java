package com.br.sanches.clientes.users.vehicle.entity;

import com.br.sanches.clientes.users.vehicle.utils.ConverterUtil;
import com.br.sanches.clientes.users.vehicle.utils.DateAndTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kafka.utils.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS_TB")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "ID_USER")
    private Long idUser;
    @Column(name = "NAME")
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "USER_NAME")
    private String userName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "PASSWORD")
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "CPF")
    private String cpf;
    @JsonDeserialize(using = DateAndTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ConverterUtil.FORMATO_DATA)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_REGISTER")
    private Date dateRegister;
    @JsonDeserialize(using = DateAndTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ConverterUtil.FORMATO_DATA)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_UPDATE")
    private Date dateUpdate;
}
