package com.crud.theatre.controller;

import com.crud.theatre.Facade.SpectacleDateFacade;
import com.crud.theatre.domain.*;
import com.crud.theatre.mapper.SpectacleDateMapper;
import com.crud.theatre.service.DateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class SpectacleDateController {

    private final SpectacleDateFacade facade;

    public SpectacleDateController(SpectacleDateFacade facade) {
        this.facade = facade;
    }

    @GetMapping(value = "/dates")
    public List<SpectacleDateDto> getSpectacleDates() {
       return facade.getSpectaclesDates();
    }

    @DeleteMapping(value = "/dates/{dateId}")
    public void delete(@PathVariable long dateId) {
        facade.deleteSpectacleDate(dateId);
    }
}
