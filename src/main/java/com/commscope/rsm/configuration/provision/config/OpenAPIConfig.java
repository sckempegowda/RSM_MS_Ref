package com.commscope.rsm.configuration.provision.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Provision",
                version = "v1.0"
        ),
        servers = @Server(url = "http://localhost:8080/provision")
)

public class OpenAPIConfig {
}