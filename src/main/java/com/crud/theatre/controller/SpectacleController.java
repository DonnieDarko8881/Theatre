package com.crud.theatre.controller;

import com.crud.theatre.Facade.SpectacleFacade;
import com.crud.theatre.domain.ActorDto;
import com.crud.theatre.domain.SpectacleDateDto;
import com.crud.theatre.domain.SpectacleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1")
public class SpectacleController {

    private final SpectacleFacade facade;

    @Autowired
    public SpectacleController(SpectacleFacade facade) {
        this.facade = facade;
    }

    @PostMapping(value = "/spectacles")
    public void save(@RequestBody SpectacleDto spectacleDto) {
        facade.save(spectacleDto);
    }

    @GetMapping(value = "/spectacles")
    public List<SpectacleDto> findAll() {
        return facade.findAll();
    }

    @GetMapping(value = "/spectacles/{spectacleId}/cast")
    public List<ActorDto> getCast(@PathVariable("spectacleId") Long spectacleId) {
        return facade.getCast(spectacleId);
    }

    @GetMapping(value = "/spectacles/{spectacleId}/dates")
    public List<SpectacleDateDto> getDates(@PathVariable("spectacleId") Long spectacleId) {
        return facade.getDates(spectacleId);
    }

    @PutMapping(value = "/spectacles/{spectacleId}/actors/{actorId}")
    public void addActorToCast(@PathVariable long spectacleId, @PathVariable long actorId) {
        facade.addActorToCast(spectacleId, actorId);
    }

    @PostMapping(value = "/spectacles/{spectacleId}/dates", consumes = APPLICATION_JSON_VALUE)
    public void saveSpectacleDate(@PathVariable("spectacleId") long spectacleId,
                                  @RequestBody SpectacleDateDto dateDto) {
        facade.saveSpectacleDate(spectacleId, dateDto);
    }

    @DeleteMapping(value = "/spectacles/{spectacleId}")
    public void delete(@PathVariable long spectacleId) {
        facade.delete(spectacleId);
    }
}
