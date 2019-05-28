package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class SeatsDto {
    private Long id;
    private int number;
    private Long stageCopyId;
    private String status;
}
