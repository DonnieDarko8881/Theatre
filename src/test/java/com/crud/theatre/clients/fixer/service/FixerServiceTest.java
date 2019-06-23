package com.crud.theatre.clients.fixer.service;

import com.crud.theatre.clients.fixer.client.FixerClient;
import com.crud.theatre.domain.Fixer.FixerEuroBaseDto;
import com.crud.theatre.domain.Fixer.Rates;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FixerServiceTest {

    @InjectMocks
    private FixerService fixerService;

    @Mock
    private FixerClient fixerClient;

    @Before
    public void init(){
        FixerEuroBaseDto fixerEuroBase = getFixerEuroBaseDto();
        when(fixerClient.getCurrentRates()).thenReturn(fixerEuroBase);
    }


    private FixerEuroBaseDto getFixerEuroBaseDto() {
        BigDecimal USD = new BigDecimal(1.2);
        BigDecimal GBP = new BigDecimal(0.8);
        BigDecimal PLN = new BigDecimal(4.0);

        return new FixerEuroBaseDto("EUR",
                new Rates(USD, GBP, PLN));
    }

    @Test
    public void getEuroFromPLN() {
        //when
        BigDecimal euroFromPLN = fixerService.getEuroFromPLN(100);

        //then
        assertEquals(new BigDecimal(25).setScale(2),euroFromPLN);
    }

    @Test
    public void getGBNFromPLN() {
        //when
        BigDecimal gbnFromPLN = fixerService.getGBNFromPLN(100);

        //then
        assertEquals(new BigDecimal(20).setScale(2),gbnFromPLN);
    }

    @Test
    public void getUSDFromPLN() {
        //when
        BigDecimal gbnFromPLN = fixerService.getUSDFromPLN(100);

        //then
        assertEquals(new BigDecimal(30).setScale(2),gbnFromPLN);
    }

    @Test
    public void ratePLNToEUR() {
        //when
        BigDecimal ratePLNtoEuro = fixerService.ratePLNToEUR();

        //then
        assertEquals(new BigDecimal(0.25).setScale(6),ratePLNtoEuro);
    }
}