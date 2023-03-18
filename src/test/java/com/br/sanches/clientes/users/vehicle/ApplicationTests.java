//package com.br.sanches.clientes.users.vehicle;
//
//import com.br.sanches.clientes.users.vehicle.controller.request.LoginRequest;
//import com.br.sanches.clientes.users.vehicle.controller.request.UserRequest;
//import com.br.sanches.clientes.users.vehicle.controller.response.UserResponse;
//import com.br.sanches.clientes.users.vehicle.entity.EntityCars;
//import com.br.sanches.clientes.users.vehicle.entity.UserEntity;
//import com.br.sanches.clientes.users.vehicle.exception.PreconditionFailedException;
//import com.br.sanches.clientes.users.vehicle.repository.CarRepository;
//import com.br.sanches.clientes.users.vehicle.repository.UserRepository;
//import com.br.sanches.clientes.users.vehicle.utils.Constants;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//import lombok.extern.slf4j.Slf4j;
////import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.testng.AssertJUnit.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration
//@AutoConfigureMockMvc
//@Slf4j
//class ApplicationTests {
//
//    private TestRestTemplate restTemplate;
//    private UserRepository userRepository;
//    private CarRepository carRepository;
//    private ConvertionsTest convertionsTest;
//    private PasswordEncoder passwordEncoder;
//    private AuthenticationManager authenticationManager;
//    private MockMvc mockMvc;
//    private String token = "";
//    String generateToken = "";
//    private final PasswordEncoder encoder;
//    private HttpServletRequest request;
//
//
//    @Autowired
//    ApplicationTests(TestRestTemplate restTemplate,
//                     UserRepository userRepository,
//                     CarRepository carRepository, ConvertionsTest convertionsTest,
//                     PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
//                     MockMvc mockMvc, PasswordEncoder encoder, HttpServletRequest request) {
//        this.restTemplate = restTemplate;
//        this.userRepository = userRepository;
//        this.carRepository = carRepository;
//        this.convertionsTest = convertionsTest;
//        this.passwordEncoder = passwordEncoder;
//        this.authenticationManager = authenticationManager;
//        this.mockMvc = mockMvc;
//        this.encoder = encoder;
//        this.request = request;
//    }
//
////    @BeforeAll
////    public void setUp() throws Exception {
////        String username = "Markin_Dev10";
////        String password = "V@lentina_2018";
////        token = shouldGenerateToken();
////    }
//
//    @Test
//    void contextLoads() {
//    }
//
//    @Test()
//    public void shouldGenerateToken() throws Exception {
//
//        String basicUserName = "Markin_Dev10";
//        String basicPassword = "V@lentina_2018";
//        String auth = basicUserName+ " : " +basicPassword;
//
//        Optional<UserEntity> userEntity = userRepository.findByUserName("Markin_Dev10");
//        assertNotNull(userEntity);
//
//        String password = "Br12-Je11-Rb87";
//        String encodedPassword = userEntity.get().getPassword();
//        if (!passwordEncoder.matches(password, encodedPassword)) {
//            System.out.println("Senhas imcompativeis");
//        }
//        assertTrue(passwordEncoder.matches(password, encodedPassword));
//
//        MultiValueMap<String, String> userNameAndPassword = new LinkedMultiValueMap<>();
//        userNameAndPassword.add("username", userEntity.get().getUserName());
//        userNameAndPassword.add("password", password);
//
//        MvcResult result = mockMvc.perform(post("http://localhost:9060/login")
//                        .params(userNameAndPassword)
//                        .header("Authorization", auth))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK);
//
//        String token = result.getResponse().getContentAsString();
//        assertNotNull(token);
//        System.out.println("Token gerado: " + token);
//    }
//
//    @Test
//    public void sholdRegisterNewUser() throws Exception {
//
//        UserRequest userRequest = ConvertionsTest.instantiatingANewUserAndVehicleForTheTest();
//
//        MockHttpServletResponse response = mockMvc.perform(
//                        MockMvcRequestBuilders.post("http://localhost:9060/clientes/users/register")
//                                .header("Authorization", "Bearer " + token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse();
//
//        Gson gson = new Gson();
//        UserResponse userResponse = gson.fromJson(response.getContentAsString(), UserResponse.class);
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED);
//
//        assertThat(userResponse.getUserName()).isEqualTo(userRequest.getUserName());
//        assertThat(userResponse.getName()).isEqualTo(userRequest.getName());
//        assertThat(userResponse.getCarResponse().getLicensePlate()).isEqualTo(userRequest.getCarRequest().getLicensePlate().toUpperCase());
//        assertThat(userResponse.getCarResponse().getVehicleModel()).isEqualTo(userRequest.getCarRequest().getVehicleModel().toUpperCase());
//        assertThat(userResponse.getCarResponse().getCountry()).isEqualTo(userRequest.getCarRequest().getCountry());
//        assertThat(userResponse.getCarResponse().getState()).isEqualTo(userRequest.getCarRequest().getState());
//        assertThat(userResponse.getCarResponse().getCity()).isEqualTo(userRequest.getCarRequest().getCity());
//
//        Optional<UserEntity> savedUser = userRepository.findByUserName(userResponse.getUserName());
//        assertThat(savedUser.isPresent()).isTrue();
//        Optional<EntityCars> savedCar = carRepository.findByLicensePlate(userRequest.getCarRequest().getLicensePlate());
//        assertThat(savedCar.isPresent()).isTrue();
//    }
//
//    @Test
//    public void sholdSearchByUserId() throws PreconditionFailedException {
//
//        ResponseEntity<UserResponse> response = this.restTemplate.getForEntity("http://localhost:9060/clientes/users/busca/1",
//                UserResponse.class);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        UserResponse userResponse = response.getBody();
//        assertThat(userResponse).isNotNull();
//
//        assertThat(userResponse.getIdUser()).isEqualTo(1L);
//        assertThat(userResponse.getUserName()).isEqualTo("Markin_Dev10");
//        assertThat(userResponse.getName()).isEqualTo("Marcos Vinicius Campos");
//        assertThat(userResponse.getCarResponse().getLicensePlate().toUpperCase()).isEqualTo("OLK1234");
//        assertThat(userResponse.getCarResponse().getVehicleModel().toUpperCase()).isEqualTo("HONDA CIVIC LXS");
//        assertThat(userResponse.getCarResponse().getCountry()).isEqualTo("Brasil");
//        assertThat(userResponse.getCarResponse().getState()).isEqualTo("São Paulo");
//        assertThat(userResponse.getCarResponse().getCity()).isEqualTo("Mirassol");
//    }
//
//    @Test
//    public void sholdUpdateUserAndCar() throws PreconditionFailedException {
//
//        UserRequest userRequest = convertionsTest.updateUserAndCarTest();
//
//        ResponseEntity<UserResponse> response = restTemplate.exchange("http://localhost:9060/clientes/users/altera-os-dados/1",
//                HttpMethod.PUT, new HttpEntity<>(userRequest),
//                UserResponse.class);
//
//        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
//
//        UserResponse userResponse = response.getBody();
//        assertNotNull(userResponse);
//        assertThat(userResponse.getUserName()).isEqualTo(userRequest.getUserName());
//        assertThat(userResponse.getName()).isEqualTo(userRequest.getName());
//        assertNotNull(userResponse.getCarResponse());
//        assertThat(userResponse.getCarResponse().getLicensePlate()).isEqualTo(userRequest.getCarRequest().getLicensePlate().toUpperCase());
//        assertThat(userResponse.getCarResponse().getVehicleModel()).isEqualTo(userRequest.getCarRequest().getVehicleModel().toUpperCase());
//        assertThat(userResponse.getCarResponse().getCountry()).isEqualTo(userRequest.getCarRequest().getCountry());
//        assertThat(userResponse.getCarResponse().getState()).isEqualTo(userRequest.getCarRequest().getState());
//        assertThat(userResponse.getCarResponse().getCity()).isEqualTo(userRequest.getCarRequest().getCity());
//    }
//
//    @Test
//    public void sholdDeleteAUserAndVehicleByItsId() throws PreconditionFailedException {
//
//        UserEntity userEntity = userRepository.findById(1L).orElse(null);
//        assertNotNull(userEntity);
//
//        EntityCars entityCars = carRepository.findById(1L).orElse(null);
//        assertNotNull(entityCars);
//
//        entityCars.setIdUser(userEntity);
//        carRepository.save(entityCars);
//
//        ResponseEntity<String> response = restTemplate.exchange("http://localhost:9060/clientes/users/deletar/1",
//                HttpMethod.DELETE, null, String.class,
//                userEntity.getIdUser());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isEqualTo(Constants.USER_DELECTED);
//
//        assertFalse(userRepository.findById(userEntity.getIdUser()).isPresent());
//        assertFalse(carRepository.findById(entityCars.getIdCar()).isPresent());
//    }
//
//    @Test
//    public void testUserLogin() throws PreconditionFailedException {
//
//        Optional<UserEntity> user = userRepository.findByUserName("Markin_Dev10");
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUserName(user.get().getUserName());
//        loginRequest.setPassword("Br12-Je11-Rb87");
//
//        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:9060/clientes/users/login-user",
//                loginRequest, String.class);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isEqualTo(Constants.LOGIN_SUCCESSES);
//    }
//}
