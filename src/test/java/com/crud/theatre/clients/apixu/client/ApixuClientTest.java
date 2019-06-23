package com.crud.theatre.clients.apixu.client;

import com.crud.theatre.clients.apixu.config.ApixuConfig;
import com.crud.theatre.domain.Apixu.ForecastTomorrowDto;
import com.crud.theatre.domain.Apixu.forecast.Forecast;
import com.crud.theatre.domain.Apixu.forecast.forcastDay.Day.Day;
import com.crud.theatre.domain.Apixu.forecast.forcastDay.Day.whetherConfiction.Condition;
import com.crud.theatre.domain.Apixu.forecast.forcastDay.ForecastDay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.helger.commons.mock.CommonsAssert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApixuClientTest {

    @InjectMocks
    private ApixuClient apixuClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApixuConfig apixuConfig;

    @Test
    public void fetchForecastForTomorrowWarsaw() throws URISyntaxException {
        //given
        List<ForecastDay> forecastDays = new ArrayList<>();
        forecastDays.add(new ForecastDay(LocalDate.now(), new Day(10.0, new Condition("test condition today"))));
        forecastDays.add(new ForecastDay(LocalDate.now().plusDays(1),
                new Day(15.0, new Condition("test condition tomorrow"))));

        ForecastTomorrowDto forecastTomorrowDto = new ForecastTomorrowDto(new Forecast(forecastDays));

        when(apixuConfig.getApixuApiEndpoint()).thenReturn("http://test.com/forecast");
        when(apixuConfig.getApixuAppKey()).thenReturn("test");
        URI uri = new URI("http://test.com/forecast?key=test&q=Warsaw&days=2");

        when(restTemplate.getForObject(uri, ForecastTomorrowDto.class)).thenReturn(forecastTomorrowDto);

        //when
        ForecastTomorrowDto forecastTomorrowWarsaw = apixuClient.getForecastTomorrow();

        //then
        ForecastDay forecastDayTomorrow = forecastTomorrowWarsaw.getForecast().getForecastday().get(1);

        assertNotNull(forecastTomorrowWarsaw.getForecast());
        assertEquals(2, forecastTomorrowWarsaw.getForecast().getForecastday().size());
        assertEquals(LocalDate.now().plusDays(1), forecastDayTomorrow.getDate());
        assertEquals(15.0,forecastDayTomorrow.getDay().getAvgtemp_c(),0.1);
        assertEquals("test condition tomorrow",forecastDayTomorrow.getDay().getCondition().getText());
    }
}