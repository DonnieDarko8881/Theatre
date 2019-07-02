package com.crud.theatre.domain.Apixu.forecast.forcastDay;

import com.crud.theatre.domain.Apixu.forecast.forcastDay.Day.Day;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastDay {
    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("day")
    private Day day;
}
