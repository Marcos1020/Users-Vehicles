package com.br.sanches.clientes.users.vehicle.controller;

import com.br.sanches.clientes.users.vehicle.basePath.BasePath;
import com.br.sanches.clientes.users.vehicle.controller.request.CarRequest;
import com.br.sanches.clientes.users.vehicle.controller.request.LoginRequest;
import com.br.sanches.clientes.users.vehicle.controller.request.UserRequest;
import com.br.sanches.clientes.users.vehicle.controller.response.UserResponse;
import com.br.sanches.clientes.users.vehicle.exception.ObjectAlreadyExists;
import com.br.sanches.clientes.users.vehicle.exception.PreconditionFailedException;
import com.br.sanches.clientes.users.vehicle.service.UserService;
import com.br.sanches.clientes.users.vehicle.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(BasePath.BASE_PATH_URL)
public class UserController{

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(BasePath.BASE_PATH_REGISTER_NEW_USER)
    public ResponseEntity<UserResponse> RegisterNewUser(
            @Valid @RequestBody UserRequest userRequest) throws ObjectAlreadyExists {
        final UserResponse response= this.userService.newUser(userRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(BasePath.BASE_PATH_ID)
    public ResponseEntity<UserResponse>searchByUserId(
            @PathVariable("id")Long idUser)throws PreconditionFailedException {
        final UserResponse response = this.userService.searchById(idUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(BasePath.BASE_PATH_ID_UPDATE)
    public ResponseEntity<UserResponse>updateUserAndCar(
            @PathVariable("id")Long idUser,
            @RequestBody UserRequest userRequest)throws PreconditionFailedException{
        final UserResponse response= this.userService.updateUserAndCar(idUser, userRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(BasePath.BASE_PATH_ID_DELETE)
    public ResponseEntity<String> deletUser(
            @PathVariable("id")Long idUser)throws PreconditionFailedException{
        this.userService.delete(idUser);
        return ResponseEntity.status(HttpStatus.OK).body(Constants.USUARIO_DELETADO);
    }

    @GetMapping(BasePath.BASE_PATH_LOGIN_USER)
    public ResponseEntity<String> UserLogin(
            @RequestBody LoginRequest loginRequest) throws PreconditionFailedException {
        this.userService.userLogin(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(Constants.LOGIN_SUCCESSES);
    }
}
