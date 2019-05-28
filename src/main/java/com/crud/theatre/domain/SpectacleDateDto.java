package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpectacleDateDto {
    private long id;
    private LocalDateTime date;
}
