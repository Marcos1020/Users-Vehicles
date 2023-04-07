package com.br.sanches.clientes.users.vehicle.client;

import com.br.sanches.clientes.users.vehicle.controller.request.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "email-client", url = "${feign.client.config.email-client.url}")
public interface EmailClient {

    @PostMapping(value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE)
    void sendEmail(final EmailRequest request);
}
