package com.crud.theatre.clients.apixu.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApixuConfig {

    @Value("${apixu.api.endpoint}")
    private String apixuApiEndpoint;

    @Value("${apixu.api.key}")
    private String apixuAppKey;
}