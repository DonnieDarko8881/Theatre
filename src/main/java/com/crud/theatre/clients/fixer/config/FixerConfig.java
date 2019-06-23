package com.crud.theatre.clients.fixer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FixerConfig {

    @Value("${fixer.api.endpoint.latest}")
    private String fixerApiEndpoint;

    @Value("${fixer.app.key}")
    private String fixerAppKey;

}
