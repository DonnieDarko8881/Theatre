package com.crud.theatre.controller;

import com.crud.theatre.domain.*;
import com.crud.theatre.service.DateService;
import com.crud.theatre.service.StageCopyService;
import com.crud.theatre.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1")
public class StageCopyController {

    private final StageService stageService;
    private final StageCopyService stageCopyService;
    private final DateService dateService;

    @Autowired
    public StageCopyController(StageService stageService, StageCopyService stageCopyService, DateService dateService) {
        this.stageService = stageService;
        this.stageCopyService = stageCopyService;
        this.dateService = dateService;
    }

    @PostMapping(value = "/stageCopy/{stageId}")
    public void saveStageCopy(@PathVariable("stageId") Long stageId,@RequestParam("dateId") long dateId) {
        Stage stage = stageService.findById(stageId);
        SpectacleDate spectacleDate = dateService.findById(dateId);
        Set<Seats> seats = new HashSet<>();
        StageCopy stageCopy = new StageCopy(stage,seats,spectacleDate);
        for (int i = 0; i <stage.getSeatsAmount() ; i++) {
            seats.add(new Seats(i+1,stageCopy, Status.FREE.toString()));
        }
        stageCopyService.save(stageCopy);
    }
}
