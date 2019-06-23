package com.crud.theatre.domain.Apixu.forecast;

import com.crud.theatre.domain.Apixu.forecast.forcastDay.ForecastDay;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
    @JsonProperty("forecastday")
    private List<ForecastDay> forecastday;
}
