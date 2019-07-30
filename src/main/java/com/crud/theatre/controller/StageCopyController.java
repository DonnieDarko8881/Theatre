package com.crud.theatre.controller;

import com.crud.theatre.domain.StageCopyDto;
import com.crud.theatre.mapper.StageCopyMapper;
import com.crud.theatre.service.StageCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class StageCopyController {

    private final StageCopyService stageCopyService;
    private final StageCopyMapper mapper;

    @Autowired
    public StageCopyController(StageCopyService stageCopyService, StageCopyMapper mapper) {
        this.stageCopyService = stageCopyService;
        this.mapper = mapper;
    }

    @PostMapping(value = "/stageCopies/{stageId}/dates/{dateId}/price/{spectaclePrice}")
    public void saveStageCopy(@PathVariable long stageId, @PathVariable long dateId, @PathVariable BigDecimal spectaclePrice) {
        stageCopyService.saveStageCopy(stageId, dateId, spectaclePrice);
    }

    @PutMapping(value = "/stageCopies/{stageCopyId}/seats/{seatsId}/status/{status}")
    public void changeStatus(@PathVariable long stageCopyId, @PathVariable long seatsId, @PathVariable String status) {
        stageCopyService.changeSeatsStatus(stageCopyId, seatsId, status);
    }

    @GetMapping(value = "/stageCopies")
    public List<StageCopyDto> getStageCopies() {
        return mapper.mapToStageCopyDtoList(stageCopyService.findAll());
    }
}
