package com.br.sanches.clientes.users.vehicle.swaggerConfig;

import com.br.sanches.clientes.users.vehicle.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(Constants.SWAGGER_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag("Clientes", Constants.SWAGGER_CLIENTS_DESCRIPTION),
                        new Tag("Veículos", Constants.SWAGGER_VEHICLES_DESCRIPTION))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(Constants.SWAGGER_TITLE_API)
                .description(Constants.SWAGGER_DESCRIPTION_API)
                .version(Constants.SWAGGER_VERSION_API)
                .contact(new Contact("One-Digital", "www.One-Digital.com", "contato@One-Digital.com"))
                .build();
    }
}