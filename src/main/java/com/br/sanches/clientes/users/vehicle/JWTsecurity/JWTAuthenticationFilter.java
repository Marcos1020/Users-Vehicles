//package com.br.sanches.clientes.users.vehicle.JWTsecurity;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.br.sanches.clientes.users.vehicle.entity.UserEntity;
//import com.br.sanches.clientes.users.vehicle.utils.Constants;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//
//public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    public static final int TOKEN_EXPERATION = 600_00;
//    public static final String TOKEN_PASSWORD = "c5b9c3de-8930-43e5-b6ef-1dccfda1b8f0";
//
//    private final AuthenticationManager authenticationManager;
//
//    @Autowired
//    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request,
//                                                HttpServletResponse response) throws AuthenticationException {
//        try {
//            UserEntity user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
//
//            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    user.getUserName(),
//                    user.getPassword(),
//                    new ArrayList<>()
//            ));
//
//        } catch (IOException error) {
//            throw new RuntimeException(Constants.AUTENTICATION_FAILED, error);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                            FilterChain chain,
//                                            Authentication authResult) throws IOException, ServletException {
//
//        SecurityUserDatils userDatils = (SecurityUserDatils) authResult.getPrincipal();
//
//        String token = JWT.create()
//                .withSubject(userDatils.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPERATION))
//                .sign(Algorithm.HMAC512(TOKEN_PASSWORD));
//
//        response.getWriter().write(token);
//        response.getWriter().flush();
//    }
//}
