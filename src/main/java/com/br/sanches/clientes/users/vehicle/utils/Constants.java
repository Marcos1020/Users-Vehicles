package com.br.sanches.clientes.users.vehicle.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public final String USER_NAME_EXISTING = "Nome de usuario já cadastrado, favor insira dados validos";
    public final String NAME_NOT_FOUND = "Nome não encontrado na base de dados";
    public final String INCORRECT_NAME = "Nome não compativel com o salvo na base de dados";
    public final String ID_NOT_FOUND = "Idt de usuario não encontrado na base de dados";
    public final String MANDATORY_FIELDS = "Todos os campos são obrigatórios";
    public final String REGISTER_NEW_USER_AND_BEHICLE = "Register new user, and vehicle";
    public final String USER_DELECTED = "Usuario deletado com sucesso";
    public final String LOGIN_SUCCESSES = "Login successful";
    public final String INITIALIZER_LOGIN_USER = "Iniciando o login do usuario";
    public final String INAVALID_PASSWORD = "Senha incorreta";
    public final String INVALID_LICENSE_PLATE = "Placa já cadastrada";
    public final String AUTENTICATION_FAILED = "Falha ao autenticar usuario";
    public final String INVALID_USER = "Usuario invalido";
    public final String SEARCH_BY_LICENSE_PLATE_FAILED = "Placa não encontrada, favor verificar se sua busca está correta!";
    public final String INVALID_VEHICLE_LICENSE_PLATE = "Placa de veiculo invalida";
    public final String VEHICLE_PLATE_ALREADY_REGISTERED = "Placa já cadastrada, verifique os dados e tente novamente";
    public final String INVALID_ENTITY = "Entidade invalida, e não pode ser salva";
    public final String SEARCH_BY_NAME = "Busca um usuario pelo nome";
    public final String SEARCH_BY_LICENSE_PLATE = "Busca um veiculo por sua placa";
    public final String UPDATE_LICENSE_PLATE = "Altera a placa de um veiculo, caso ela seja cadastrada errada";
    public final String UPDATE_VEHICLE_MODEL = "Altera o modelo de um veiculo, caso ela seja cadastrado errado";
    public final String UPDATE_A_USER_AND_A_VEHICLES_LOCATION_DATA = "Atualiza os dados de um usuario e" +
            " os dados cadastrais da localização de seu veiculo";
    public final String DELETE_USER_AND_VEHICLE_BY_IDUSER = "Deleta um usuario e um veiculo que esteja vinculado com o mesmo identificador";
    public final String SWAGGER_CLIENTS_DESCRIPTION = "Cadastro de usuarios. alterações de dados, buscas e exclusão de dados";
    public final String SWAGGER_TITLE_API = "VEHICLES AND CLIENTS";
    public final String SWAGGER_DESCRIPTION_API = "Search vehicles by license plate and owner identifier";
    public final String SWAGGER_BASE_PACKAGE = "com.br.sanches.clientes.users.vehicle.controller";
    public final String SWAGGER_VERSION_API = "1.0.0";
    public final String SWAGGER_VEHICLES_DESCRIPTION ="Cadastro de veiculos, alteração de placa e modelo de veiculos," +
            "alteração da região onde o dono do automovel reside e exclusão de dados";

}
