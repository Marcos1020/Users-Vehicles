package com.br.sanches.clientes.users.vehicle.controller;

import com.br.sanches.clientes.users.vehicle.basePath.BasePath;
import com.br.sanches.clientes.users.vehicle.controller.request.LoginRequest;
import com.br.sanches.clientes.users.vehicle.controller.request.UpdateLicensePlateOrModelVehicleRequest;
import com.br.sanches.clientes.users.vehicle.controller.request.UserRequest;
import com.br.sanches.clientes.users.vehicle.controller.response.UserResponse;
import com.br.sanches.clientes.users.vehicle.entity.EntityCars;
import com.br.sanches.clientes.users.vehicle.exception.BadRequestException;
import com.br.sanches.clientes.users.vehicle.exception.ObjectAlreadyExists;
import com.br.sanches.clientes.users.vehicle.exception.PreconditionFailedException;
import com.br.sanches.clientes.users.vehicle.service.UserService;
import com.br.sanches.clientes.users.vehicle.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(BasePath.BASE_PATH_URL)
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(BasePath.BASE_PATH_REGISTER_NEW_USER)
    public ResponseEntity<?> RegisterNewUser(
            @Valid @RequestBody UserRequest userRequest) throws ObjectAlreadyExists {
        try {
            final UserResponse response = this.userService.newUser(userRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (ObjectAlreadyExists exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exists.getMessage());
        }
    }

    @GetMapping(BasePath.BASE_PARAM_NAME)
    public ResponseEntity<?> searchByUserId(
            @RequestParam(
                    name = "name",
                    required = true,
                    value = "name") final String name) throws PreconditionFailedException {
        try {
            final UserResponse response = this.userService.searchByName(name);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (PreconditionFailedException exception) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(exception.getMessage());
        }
    }

    @PutMapping(BasePath.BASE_PATH_ID_UPDATE)
    public ResponseEntity<?> updateUserAndCar(
            @PathVariable("id") Long idUser,
            @RequestBody UserRequest userRequest) throws BadRequestException {
        try {
            final UserResponse response = this.userService.updateUserAndCar(idUser, userRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (PreconditionFailedException exception) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(exception.getMessage());
        }
    }

    @DeleteMapping(BasePath.BASE_PATH_ID_DELETE)
    public ResponseEntity<?> deletUser(
            @PathVariable("id") Long idUser) throws PreconditionFailedException {
        try {
            this.userService.delete(idUser);
            return ResponseEntity.status(HttpStatus.OK).body(Constants.USER_DELECTED);

        } catch (PreconditionFailedException exception) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(exception.getMessage());
        }
    }

    @PostMapping(BasePath.BASE_PATH_LOGIN_USER)
    public ResponseEntity<?> UserLogin(
            @RequestBody LoginRequest loginRequest) throws PreconditionFailedException {
        try {
            this.userService.userLogin(loginRequest);
            return ResponseEntity.status(HttpStatus.OK).body(Constants.LOGIN_SUCCESSES);

        } catch (PreconditionFailedException exception) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(exception.getMessage());
        }
    }

    @GetMapping(BasePath.BASE_PARAM_SEARCH_LICENSE_PLATE)
    public ResponseEntity<?> searchByPlateVehicle(
            @RequestParam(
                    name = "licensePlate",
                    required = true,
                    value = "licensePlate") String licensePlate) throws PreconditionFailedException {
        try {
            EntityCars car = userService.searchByLicensePlate(licensePlate);
            return ResponseEntity.ok(car);

        } catch (PreconditionFailedException exception) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(exception.getMessage());
        }
    }

    @PatchMapping(BasePath.BASE_PATH_ALTER_LICENSE_PLATE)
    public ResponseEntity<?> changingWronglyRegisteredPlate(
            @RequestParam(
                    name = "idUser",
                    required = true,
                    value = "idUser") final Long idUser,
            @RequestBody UpdateLicensePlateOrModelVehicleRequest request) throws BadRequestException {
        try {
            UserResponse response = this.userService.updateLicensePlate(idUser, request);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (BadRequestException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PatchMapping(BasePath.BASE_PATH_ALTER_VEHICLE_MODEL)
    public ResponseEntity<?> changingWronglyRegisteredVehicle(
            @RequestParam(
                    name = "licensePlate",
                    required = true,
                    value = "licensePlate")final String licensePlate,
            @RequestBody UpdateLicensePlateOrModelVehicleRequest request)throws BadRequestException{
        try {
            EntityCars response = this.userService.updateVehicleModel(licensePlate, request);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (BadRequestException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}