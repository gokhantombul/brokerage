package com.inghubstr.brokerage.v1.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${brokerage.openapi.dev-url}")
    private String devUrl;

    @Value("${brokerage.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("gokhantombul@hotmail.com");
        contact.setName("Gokhan Tombul");
        contact.setUrl("https://linkedin.com/in/gokhantombul");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Brokerage API")
                .version("1.0")
                .contact(contact)
                .description("This backend API is designed for a brokerage firm to manage customer stock trading activities. It supports creating, listing, canceling, and matching stock orders, as well as tracking customer assets including TRY. All endpoints are secured with role-based access control for admins and customers.").termsOfService("https://www.test.com/terms")
                .license(mitLicense);

        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("bearerAuth");

        Components components = new Components();
        components.addSecuritySchemes("bearerAuth",
                new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer)).addSecurityItem(securityRequirement).components(components);
    }

}
