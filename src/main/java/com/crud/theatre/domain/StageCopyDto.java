package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class StageCopyDto {
    private Long id;
    private List<SeatsDto> seats;
    private SpectacleDateDto spectacleDateDto;
    private BigDecimal spectaclePricePLN;
}
