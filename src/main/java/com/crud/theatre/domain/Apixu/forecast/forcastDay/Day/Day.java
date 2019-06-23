package com.crud.theatre.domain.Apixu.forecast.forcastDay.Day;

import com.crud.theatre.domain.Apixu.forecast.forcastDay.Day.whetherConfiction.Condition;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Day {
    @JsonProperty("avgtemp_c")
    private double avgtemp_c;
    @JsonProperty("condition")
    private Condition condition;
}


