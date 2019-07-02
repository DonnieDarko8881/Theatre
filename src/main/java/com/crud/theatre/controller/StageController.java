package com.crud.theatre.controller;

import com.crud.theatre.domain.SpectacleDto;
import com.crud.theatre.domain.Stage;
import com.crud.theatre.domain.StageDto;
import com.crud.theatre.mapper.SpectacleMapper;
import com.crud.theatre.mapper.StageMapper;
import com.crud.theatre.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1")
public class StageController {

    private final StageMapper stageMapper;
    private final StageService stageService;
    private final SpectacleMapper spectacleMapper;

    @Autowired
    public StageController(StageMapper stageMapper, StageService stageService,
                           SpectacleMapper spectacleMapper) {
        this.stageMapper = stageMapper;
        this.stageService = stageService;
        this.spectacleMapper = spectacleMapper;
    }

    @PostMapping(value = "/stages")
    public void save(@RequestBody StageDto stageDto) {
        stageService.save(stageMapper.mapToStage(stageDto));
    }

    @GetMapping(value = "/stages")
    public List<StageDto> getStages(){
        return stageMapper.mapToStageDtoList(stageService.findAll());
    }

    @PutMapping(value = "/stages")
    public StageDto updateStage(@RequestBody StageDto stageDto){
        return stageMapper.mapToStageDto(stageService.save(stageMapper.mapToStage(stageDto)));
    }

    @GetMapping(value = "/stages/{stageId}/spectacles")
    public Set<SpectacleDto> getSpectaclesFromStage(@PathVariable("stageId") Long stageId) {
        Stage stage = stageService.findById(stageId);
        return spectacleMapper.mapToSpectacleDtoSet(stage.getSpectacles());
    }
}
