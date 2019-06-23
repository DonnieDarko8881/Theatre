package com.crud.theatre.clients.apixu.client;

import com.crud.theatre.clients.apixu.config.ApixuConfig;
import com.crud.theatre.domain.Apixu.ForecastTomorrowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class ApixuClient {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApixuConfig apixuConfig;

    public ForecastTomorrowDto getForecastTomorrow() {
        URI uri = getUriForecastTomorrowWarsaw();
        return restTemplate.getForObject(uri, ForecastTomorrowDto.class);
    }

    private URI getUriForecastTomorrowWarsaw() {
        return UriComponentsBuilder.fromHttpUrl(apixuConfig.getApixuApiEndpoint())
                .queryParam("key", apixuConfig.getApixuAppKey())
                .queryParam("q", "Warsaw")
                .queryParam("days",2)
                .build().encode().toUri();
    }
}
