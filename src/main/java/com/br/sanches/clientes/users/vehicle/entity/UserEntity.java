package com.br.sanches.clientes.users.vehicle.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USERS_TB")
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "ID_USER")
    private Long idUser;

    @NotEmpty(message = "O campo cpf é obrigatório")
    @Column(name = "NAME")
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "Email")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "PASSWORD")
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "O campo cpf é obrigatório")
    @Size(min = 10, message = "CPF deve conter no minimo 11 caracters")
    @Column(name = "CPF")
    private String cpf;

    @Column(name = "DT_REGISTER")
    private LocalDate dateRegister;

    @Column(name = "DT_UPDATE")
    private LocalDate dateUpdate;
}
