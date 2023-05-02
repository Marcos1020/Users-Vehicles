package com.br.sanches.clientes.users.vehicle.basePath;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BasePath {

    public final String BASE_PATH_URL = "v1/api/clientes/users";
    public final String BASE_PATH_REGISTER_NEW_USER = "/register";
    public final String BASE_PARAM_NAME = "/search";
    public final String BASE_PATH_ID_UPDATE = "/altera-os-dados";
    public final String BASE_PATH_ID_DELETE = "/deletar/{id}";
    public final String BASE_PATH_LOGIN_USER = "/login-user";
    public final String BASE_PARAM_SEARCH_LICENSE_PLATE = "/search/license-plate";
    public final String BASE_PATH_ALTER_LICENSE_PLATE = "/alter/license-plate";
    public final String BASE_PATH_ALTER_VEHICLE_MODEL = "/alter/vehicle-model";
}
