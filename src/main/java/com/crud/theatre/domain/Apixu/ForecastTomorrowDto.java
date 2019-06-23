package com.crud.theatre.domain.Apixu;

import com.crud.theatre.domain.Apixu.forecast.Forecast;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastTomorrowDto {
    @JsonProperty("forecast")
    private Forecast forecast;
}
