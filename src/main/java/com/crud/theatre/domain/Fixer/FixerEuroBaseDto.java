package com.crud.theatre.domain.Fixer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixerEuroBaseDto {

    @JsonProperty("base")
    private String base;
    @JsonProperty("rates")
    private Rates rates;

}
