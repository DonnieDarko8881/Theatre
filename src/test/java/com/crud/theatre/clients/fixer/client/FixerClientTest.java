package com.crud.theatre.clients.fixer.client;

import com.crud.theatre.clients.fixer.config.FixerConfig;
import com.crud.theatre.domain.Fixer.FixerEuroBaseDto;
import com.crud.theatre.domain.Fixer.Rates;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FixerClientTest {

    @InjectMocks
    private FixerClient fixerClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private FixerConfig fixerConfig;

    @Test
    public void fetchCurrentCurrencyRates() throws URISyntaxException {
        //Given
        BigDecimal USD = new BigDecimal(1.2);
        BigDecimal GBP = new BigDecimal(0.8);
        BigDecimal PLN = new BigDecimal(4.0);

        FixerEuroBaseDto fixerEuroBaseDto = new FixerEuroBaseDto("EUR",
                new Rates(USD, GBP, PLN));

        when(fixerConfig.getFixerApiEndpoint()).thenReturn("http://test.com/apixu");
        when(fixerConfig.getFixerAppKey()).thenReturn("test");
        URI uri = new URI("http://test.com/apixu?access_key=test&symbols=USD,PLN,GBP");

        when(restTemplate.getForObject(uri, FixerEuroBaseDto.class)).thenReturn(fixerEuroBaseDto);

        //when
        FixerEuroBaseDto currentRates = fixerClient.getCurrentRates();

        //then
        assertNotNull(currentRates);
        assertEquals("EUR", currentRates.getBase());
        assertEquals(USD, currentRates.getRates().getUSD());
        assertEquals(GBP, currentRates.getRates().getGBP());
        assertEquals(PLN, currentRates.getRates().getPLN());
    }
}