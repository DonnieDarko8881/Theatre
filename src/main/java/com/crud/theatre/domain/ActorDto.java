package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ActorDto {
    private long id;
    private String firstName;
    private String lastName;

    public ActorDto(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ActorDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
