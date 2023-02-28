package com.br.sanches.clientes.users.vehicle;

import com.br.sanches.clientes.users.vehicle.controller.request.CarRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
	@Autowired
	ApplicationTests(TestRestTemplate restTemplate, UserRepository userRepository, CarRepository carRepository) {
		this.restTemplate = restTemplate;
		this.userRepository = userRepository;
		this.carRepository = carRepository;
	}

	@Test
	void contextLoads() {
	}
	private TestRestTemplate restTemplate;

	private UserRepository userRepository;

	private CarRepository carRepository;

	@Test
	public void testRegisterNewUser() throws PreconditionFailedException {

		UserRequest userRequest = new UserRequest();
		userRequest.setUserName("Markin_Dev10");
		userRequest.setName("Marcos Vinicius Campos");
		userRequest.setCpf("93610007864");
		userRequest.setPassword("Br12-Je11-Rb87");
		CarRequest carRequest = new CarRequest();
		carRequest.setLicensePlate("olk1234");
		carRequest.setVehicleModel("honda civic lxs");
		carRequest.setCountry("Brasil");
		carRequest.setState("São Paulo");
		carRequest.setCity("Mirassol");
		userRequest.setCarRequest(carRequest);

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

}
