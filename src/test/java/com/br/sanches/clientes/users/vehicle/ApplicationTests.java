package com.br.sanches.clientes.users.vehicle;

import com.br.sanches.clientes.users.vehicle.controller.request.UserRequest;
import com.br.sanches.clientes.users.vehicle.controller.response.UserResponse;
import com.br.sanches.clientes.users.vehicle.entity.EntityCars;
import com.br.sanches.clientes.users.vehicle.entity.UserEntity;
import com.br.sanches.clientes.users.vehicle.exception.PreconditionFailedException;
import com.br.sanches.clientes.users.vehicle.repository.CarRepository;
import com.br.sanches.clientes.users.vehicle.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    private TestRestTemplate restTemplate;

    private UserRepository userRepository;

    private CarRepository carRepository;

    private ConvertionsTest convertionsTest;

    @Autowired
    ApplicationTests(TestRestTemplate restTemplate, UserRepository userRepository, CarRepository carRepository, ConvertionsTest convertionsTest) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.convertionsTest = convertionsTest;
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void sholdRegisterNewUser() throws PreconditionFailedException {

        UserRequest userRequest = convertionsTest.instantiatingANewUserAndVehicleForTheTest();

        ResponseEntity<UserResponse> response = restTemplate.postForEntity(
                "http://localhost:9060/clientes/users/register", userRequest, UserResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        UserResponse userResponse = response.getBody();
        assertThat(userResponse.getUserName()).isEqualTo(userRequest.getUserName());
        assertThat(userResponse.getName()).isEqualTo(userRequest.getName());
        assertThat(userResponse.getCarResponse().getLicensePlate()).isEqualTo(userRequest.getCarRequest().getLicensePlate().toUpperCase());
        assertThat(userResponse.getCarResponse().getVehicleModel()).isEqualTo(userRequest.getCarRequest().getVehicleModel().toUpperCase());
        assertThat(userResponse.getCarResponse().getCountry()).isEqualTo(userRequest.getCarRequest().getCountry());
        assertThat(userResponse.getCarResponse().getState()).isEqualTo(userRequest.getCarRequest().getState());
        assertThat(userResponse.getCarResponse().getCity()).isEqualTo(userRequest.getCarRequest().getCity());

        Optional<UserEntity> savedUser = userRepository.findByUserName(userResponse.getUserName());
        assertThat(savedUser.isPresent()).isTrue();
        Optional<EntityCars> savedCar = carRepository.findByLicensePlate(userRequest.getCarRequest().getLicensePlate());
        assertThat(savedCar.isPresent()).isTrue();
    }

    @Test
    public void sholdSearchByUserId() throws PreconditionFailedException {

        ResponseEntity<UserResponse> response = this.restTemplate.getForEntity(
                "http://localhost:9060/clientes/users/busca/1",
                UserResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserResponse userResponse = response.getBody();
        assertThat(userResponse).isNotNull();

        assertThat(userResponse.getIdUser()).isEqualTo(1L);
        assertThat(userResponse.getUserName()).isEqualTo("Markin_Dev10");
        assertThat(userResponse.getName()).isEqualTo("Marcos Vinicius Campos");
        assertThat(userResponse.getCarResponse().getLicensePlate().toUpperCase()).isEqualTo("OLK1234");
        assertThat(userResponse.getCarResponse().getVehicleModel().toUpperCase()).isEqualTo("HONDA CIVIC LXS");
        assertThat(userResponse.getCarResponse().getCountry()).isEqualTo("Brasil");
        assertThat(userResponse.getCarResponse().getState()).isEqualTo("São Paulo");
        assertThat(userResponse.getCarResponse().getCity()).isEqualTo("Mirassol");
    }

    @Test
    public void sholdUpdateUserAndCar() throws PreconditionFailedException {

        UserRequest userRequest = convertionsTest.updateUserAndCarTest();

        ResponseEntity<UserResponse> response = restTemplate.exchange(
                "http://localhost:9060/clientes/users/altera-os-dados/1",
                HttpMethod.PUT,
                new HttpEntity<>(userRequest),
                UserResponse.class);

        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());

        UserResponse userResponse = response.getBody();
        assertNotNull(userResponse);
        assertThat(userResponse.getUserName()).isEqualTo(userRequest.getUserName());
        assertThat(userResponse.getName()).isEqualTo(userRequest.getName());
        assertNotNull(userResponse.getCarResponse());
        assertThat(userResponse.getCarResponse().getLicensePlate()).isEqualTo(userRequest.getCarRequest().getLicensePlate().toUpperCase());
        assertThat(userResponse.getCarResponse().getVehicleModel()).isEqualTo(userRequest.getCarRequest().getVehicleModel().toUpperCase());
        assertThat(userResponse.getCarResponse().getCountry()).isEqualTo(userRequest.getCarRequest().getCountry());
        assertThat(userResponse.getCarResponse().getState()).isEqualTo(userRequest.getCarRequest().getState());
        assertThat(userResponse.getCarResponse().getCity()).isEqualTo(userRequest.getCarRequest().getCity());
    }

    @Test
    public void sholdDeleteAUserAndVehicleByItsId() {

        UserEntity userEntity = userRepository.findById(1L).orElse(null);
        assertNotNull(userEntity);

        EntityCars entityCars = carRepository.findById(1L).orElse(null);
        assertNotNull(entityCars);

        entityCars.setIdUser(userEntity);
        carRepository.save(entityCars);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:9060/clientes/users/deletar/1",
                HttpMethod.DELETE,
                null,
                String.class,
                userEntity.getIdUser()
        );
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertFalse(userRepository.findById(userEntity.getIdUser()).isPresent());
        assertFalse(carRepository.findById(entityCars.getIdCar()).isPresent());

    }
}
