package com.crud.theatre.clients.fixer.service;

import com.crud.theatre.clients.fixer.client.FixerClient;
import com.crud.theatre.domain.Fixer.FixerEuroBaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class FixerService {
    private final FixerClient fixerClient;

    @Autowired
    public FixerService(FixerClient fixerClient) {
        this.fixerClient = fixerClient;
    }

    public BigDecimal getEuroFromPLN(double amountPLN) {
        return ratePLNToEUR().multiply(new BigDecimal(amountPLN)).setScale(2,RoundingMode.HALF_UP);
    }

    public BigDecimal getGBNFromPLN(double amountPLN){
        BigDecimal gbp = getFixerEuroBaseDto().getRates().getGBP();
        return ratePLNToEUR().multiply(gbp).multiply(new BigDecimal(amountPLN)).setScale(2,RoundingMode.HALF_UP);
    }

    public BigDecimal getUSDFromPLN(double amountPLN){
        BigDecimal euroRateToUSD = getFixerEuroBaseDto().getRates().getUSD();
        return ratePLNToEUR().multiply(euroRateToUSD).multiply(new BigDecimal(amountPLN)).setScale(2,RoundingMode.HALF_UP);
    }

    public BigDecimal ratePLNToEUR() {
        return new BigDecimal(1).divide(getFixerEuroBaseDto().getRates().getPLN(), 6, RoundingMode.HALF_UP);
    }

    private FixerEuroBaseDto getFixerEuroBaseDto(){
        return fixerClient.getCurrentRates();
    }
}
