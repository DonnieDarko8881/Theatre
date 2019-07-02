package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpectacleDto {
    private long id;
    private String name;
    private Long stageId;

    public SpectacleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
