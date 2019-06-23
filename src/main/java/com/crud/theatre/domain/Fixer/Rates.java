package com.crud.theatre.domain.Fixer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rates {
    @JsonProperty("USD")
    private BigDecimal USD;
    @JsonProperty("GBP")
    private BigDecimal GBP;
    @JsonProperty("PLN")
    private BigDecimal PLN;

    @Override
    public String toString() {
        return "Rates{" +
                "USD='" + USD + '\'' +
                ", CAD='" + GBP + '\'' +
                ", PLN='" + PLN + '\'' +
                '}';
    }
}
