package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StageDto {
    private Long id;
    private String name;
    private int seatsAmount;
}
