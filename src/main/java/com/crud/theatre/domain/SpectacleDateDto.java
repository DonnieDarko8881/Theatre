package com.crud.theatre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpectacleDateDto {
    private long id;
    @SerializedName(value = "spectacleDate")
    private LocalDateTime date;
    private SpectacleDto spectacleDto;
    private StageDto stageDto;
    private StageCopyDto stageCopyDto;
}
