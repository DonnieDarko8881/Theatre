package com.crud.theatre.clients.fixer.client;

import com.crud.theatre.domain.Fixer.FixerEuroBaseDto;
import com.crud.theatre.clients.fixer.config.FixerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Component
public class FixerClient {
//    private static final Logger LOGGER = LoggerFactory.getLogger(FixerClient.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FixerConfig fixerConfig;

    public FixerEuroBaseDto getCurrentRates() {
        URI uri = getUriFetchCurrentCurrency();
        return restTemplate.getForObject(uri, FixerEuroBaseDto.class);
    }

    private URI getUriFetchCurrentCurrency() {
        return UriComponentsBuilder.fromHttpUrl(fixerConfig.getFixerApiEndpoint())
                .queryParam("access_key", fixerConfig.getFixerAppKey())
                .queryParam("symbols", "USD,PLN,GBP")
                .build().encode().toUri();
    }


}
