package com.crud.theatre.controller;

import com.crud.theatre.domain.SpectacleDto;
import com.crud.theatre.domain.Stage;
import com.crud.theatre.domain.StageDto;
import com.crud.theatre.mapper.SeatsMapper;
import com.crud.theatre.mapper.SpectacleMapper;
import com.crud.theatre.mapper.StageMapper;
import com.crud.theatre.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class StageController {

    private final StageMapper stageMapper;
    private final StageService stageService;
    private final SpectacleMapper spectacleMapper;
    private final SeatsMapper seatsMapper;

    @Autowired
    public StageController(StageMapper stageMapper, StageService stageService,
                           SpectacleMapper spectacleMapper, SeatsMapper seatsMapper) {
        this.stageMapper = stageMapper;
        this.stageService = stageService;
        this.spectacleMapper = spectacleMapper;
        this.seatsMapper = seatsMapper;
    }

    @PostMapping(value = "/stages")
    public void save(@RequestBody StageDto stageDto) {
        stageService.save(stageMapper.mapToStage(stageDto));
    }

//    @PostMapping(value = "/stages/{stageId}/seats")
//    public void addAmountOfSeats(@PathVariable("stageId") Long stageId, @RequestParam int howManySeats){
//        Stage stage = stageService.findById(stageId);
//        for (int i = 0; i < howManySeats; i++) {
//            stage.getSeats().add(new Seats(i+1,stage, Status.FREE.toString()));
//            stageService.save(stage);
//        }
//    }

//    @GetMapping(value = "/stages/{stageId}/seats")
//    public List<SeatsDto> getSeats(@PathVariable("stageId") Long stageId){
//        Stage stage = stageService.findById(stageId);
//        return seatsMapper.mapToSeatsDtoList(stage.getSeats());
//    }

    @GetMapping(value = "/stages/{stageId}/spectacles")
    public List<SpectacleDto> getSpectaclesFromStage(@PathVariable("stageId") Long stageId) {
        Stage stage = stageService.findById(stageId);
        return spectacleMapper.mapToSpectacleDtoList(stage.getSpectacles());
    }
}
