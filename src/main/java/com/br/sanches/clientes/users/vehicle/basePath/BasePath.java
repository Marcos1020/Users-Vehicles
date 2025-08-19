package com.br.sanches.clientes.users.vehicle.basePath;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BasePath {

    public final String BASE_PATH_URL = "v1/api/clientes/users/";
    public final String BASE_PATH_REGISTER_NEW_USER = "register";
    public final String BASE_PATH_ID = "busca/{id}";
    public final String BASE_PATH_ID_UPDATE = "altera-os-dados/{id}";
    public final String BASE_PATH_ID_DELETE = "deletar/{id}";
    public final String BASE_PATH_LOGIN_USER = "login-user";
    public final String BASE_PATH_TOKEN = "generate/token";
}
