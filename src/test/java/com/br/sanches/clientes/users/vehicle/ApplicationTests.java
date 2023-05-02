package com.br.sanches.clientes.users.vehicle;

import com.br.sanches.clientes.users.vehicle.controller.request.LoginRequest;
import com.br.sanches.clientes.users.vehicle.controller.request.UpdateLicensePlateOrModelVehicleRequest;
import com.br.sanches.clientes.users.vehicle.controller.request.UserRequest;
import com.br.sanches.clientes.users.vehicle.controller.response.UserResponse;
import com.br.sanches.clientes.users.vehicle.entity.EntityCars;
import com.br.sanches.clientes.users.vehicle.entity.UserEntity;
import com.br.sanches.clientes.users.vehicle.repository.CarRepository;
import com.br.sanches.clientes.users.vehicle.repository.UserRepository;
import com.br.sanches.clientes.users.vehicle.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@AutoConfigureMockMvc
@Slf4j
class ApplicationTests {

    private TestRestTemplate restTemplate;
    private UserRepository userRepository;
    private CarRepository carRepository;
    private ConvertionsTest convertionsTest;
    private PasswordEncoder passwordEncoder;
    private final PasswordEncoder encoder;
    private HttpServletRequest request;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @Autowired
    ApplicationTests(TestRestTemplate restTemplate,
                     UserRepository userRepository,
                     CarRepository carRepository, ConvertionsTest convertionsTest,
                     PasswordEncoder passwordEncoder,
                     PasswordEncoder encoder, HttpServletRequest request, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.convertionsTest = convertionsTest;
        this.passwordEncoder = passwordEncoder;
        this.encoder = encoder;
        this.request = request;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void shouldRegisterNewUser() throws Exception {

        UserRequest userRequest = ConvertionsTest.instantiatingANewUserAndVehicleForTheTest();
        String jsonRequest = objectMapper.writeValueAsString(userRequest);

        MvcResult mvcResult = mockMvc.perform(post("v1/api/clientes/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8"));
        UserResponse userResponse = objectMapper.readValue(jsonResponse, UserResponse.class);

        assertThat(userResponse.getName()).isEqualTo(userRequest.getName());
        assertThat(userResponse.getCarResponse().getLicensePlate())
                .isEqualTo(userRequest.getCarRequest().getLicensePlate().toUpperCase());
        assertThat(userResponse.getCarResponse().getVehicleModel())
                .isEqualTo(userRequest.getCarRequest().getVehicleModel().toUpperCase());
        assertThat(userResponse.getCarResponse().getCountry())
                .isEqualTo(userRequest.getCarRequest().getCountry());
        assertThat(userResponse.getCarResponse().getState())
                .isEqualTo(userRequest.getCarRequest().getState());
        assertThat(userResponse.getCarResponse().getCity())
                .isEqualTo(userRequest.getCarRequest().getCity());
        assertThat(userResponse.getEmail()).isEqualTo(userRequest.getEmail());
    }

    @Test
    public void shouldSearchbyName() throws Exception {

        MvcResult result = mockMvc.perform(get("v1/api/clientes/users/search")
                        .param("name", "Marcos Vinicius Campos"))
                .andExpect(status().isOk())
                .andReturn();

        UserResponse userResponse = objectMapper.readValue(result.getResponse()
                .getContentAsString(Charset.forName("UTF-8")), UserResponse.class);

        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getIdUser()).isEqualTo(1L);
        assertThat(userResponse.getName()).isEqualTo("Marcos Vinicius Campos");
        assertThat(userResponse.getCarResponse().getLicensePlate().toUpperCase()).isEqualTo("OLK1234");
        assertThat(userResponse.getCarResponse().getVehicleModel().toUpperCase()).isEqualTo("HONDA CIVIC LXS");
        assertThat(userResponse.getCarResponse().getCountry()).isEqualTo("Brasil");
        assertThat(userResponse.getCarResponse().getState()).isEqualTo("São Paulo");
        assertThat(userResponse.getCarResponse().getCity()).isEqualTo("Mirassol");
    }

    @Test
    public void sholdUpdateUserAndCar() throws Exception {

        UserRequest userRequest = convertionsTest.updateUserAndCarTest();
        String jsonRequest = objectMapper.writeValueAsString(userRequest);

        MvcResult result = mockMvc.perform(put("v1/api/clientes/users/altera-os-dados")
                        .param("idUser", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();

        UserResponse userResponse = objectMapper.readValue(result.getResponse()
                .getContentAsString(Charset.forName("UTF-8")), UserResponse.class);

        assertNotNull(userResponse);
        assertThat(userResponse.getName()).isEqualTo(userRequest.getName());
        assertNotNull(userResponse.getCarResponse());
        assertThat(userResponse.getCarResponse().getCountry()).isEqualTo(userRequest.getCarRequest().getCountry());
        assertThat(userResponse.getCarResponse().getState()).isEqualTo(userRequest.getCarRequest().getState());
        assertThat(userResponse.getCarResponse().getCity()).isEqualTo(userRequest.getCarRequest().getCity());
    }

    @Test
    public void shouldDeleteAUserAndVehicleByItsId() throws Exception {

        UserEntity userEntity = userRepository.findById(Long.valueOf(1L)).orElse(null);
        assertNotNull(userEntity);

        EntityCars entityCars = carRepository.findById(Long.valueOf(1L)).orElse(null);
        assertNotNull(entityCars);

        entityCars.setUserEntity(userEntity);

        carRepository.save(entityCars);

        mockMvc.perform(delete("v1/api/clientes/users/deletar/1", userEntity.getIdUser()))
                .andExpect(status().isOk())
                .andExpect(content().string(Constants.USER_DELECTED));

        assertFalse(userRepository.findById(userEntity.getIdUser()).isPresent());
        assertFalse(carRepository.findById(entityCars.getIdCar()).isPresent());
    }

    @Test
    public void sholdReturnOkUserLogin() throws Exception {

        Optional<UserEntity> user = userRepository.findByUserName("Markin_Dev10");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(user.get().getUserName());
        loginRequest.setPassword("Br12-Je11-Rb87");

        MvcResult mvcResult = mockMvc.perform(post("v1/api/clientes/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8"));
        assertThat(responseBody).isEqualTo(Constants.LOGIN_SUCCESSES);
    }

    @Test
    public void sholdReturnCarDataByYourLicensePlate() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                "v1/api/clientes/users/search/license-plate")
                        .param("licensePlate", "OLK1234"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8"));
        EntityCars carResponse = objectMapper.readValue(responseJson, EntityCars.class);

        assertThat(carResponse).isNotNull();
        assertThat(carResponse.getUserEntity().getName()).isEqualTo("Marcos Vinicius Campos");
        assertThat(carResponse.getLicensePlate().toUpperCase()).isEqualTo("OLK1234");
        assertThat(carResponse.getVehicleModel().toUpperCase()).isEqualTo("HONDA CIVIC LXS");
        assertThat(carResponse.getCountry()).isEqualTo("Brasil");
        assertThat(carResponse.getState()).isEqualTo("São Paulo");
        assertThat(carResponse.getCity()).isEqualTo("Mirassol");
    }

    @Test
    public void mustChangeTheLicensePlateOfAVehicleThatWasRegisteredWrong() throws Exception {

        UpdateLicensePlateOrModelVehicleRequest userRequest = convertionsTest.changeTheLicensePlateOfAVehicle();
        String jsonRequest = objectMapper.writeValueAsString(userRequest);

        MvcResult mvcResult = mockMvc.perform(patch("v1/api/clientes/users/alter/license-plate")
                        .param("idUser", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8"));
        UserResponse userResponse = objectMapper.readValue(responseJson, UserResponse.class);

        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getCarResponse().getLicensePlate().toUpperCase()).isEqualTo("EFL0824");
    }

    @Test
    public void mustChangeTheModelOfAVehicleThatWasRegisteredIncorrectly() throws Exception {

        UpdateLicensePlateOrModelVehicleRequest updateRequest = convertionsTest.changesTheModelOfAVehicleOfAVehicle();
        String jsonRequest = objectMapper.writeValueAsString(updateRequest);

        MvcResult mvcResult = mockMvc.perform(patch("v1/api/clientes/users/alter/vehicle-model")
                        .param("licensePlate", "EFL0824")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8"));
        EntityCars response = objectMapper.readValue(responseJson, EntityCars.class);

        assertThat(response).isNotNull();
        assertThat(response.getVehicleModel().toUpperCase()).isEqualTo("GOLF GTI 1.8");
    }
}