package com.br.sanches.clientes.users.vehicle.emailIntegration;

import com.br.sanches.clientes.users.vehicle.controller.request.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "email-client", url = "${feign.client.config.email-client.url}")
public interface EmailIntegration {

    @PostMapping(value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE)
    void sendEmail(final EmailRequest request);
}
