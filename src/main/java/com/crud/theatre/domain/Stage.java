package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity(name = "stage")
public class Stage {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "seats_amount")
    private int seatsAmount;

    @OneToMany(
            targetEntity = Spectacle.class,
            mappedBy = "stage",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<Spectacle> spectacles = new HashSet<>();

    @OneToMany(
            targetEntity = SpectacleDate.class,
            mappedBy = "stage",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<SpectacleDate> dates = new HashSet<>();

    @OneToMany(
            targetEntity = StageCopy.class,
            mappedBy = "stage",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<StageCopy> stageCopies = new HashSet<>();

    public Stage(String name, int seatsAmount) {
        this.name = name;
        this.seatsAmount = seatsAmount;
    }

    public Stage(long id,String name, int seatsAmount) {
        this.id =id;
        this.name = name;
        this.seatsAmount = seatsAmount;
    }

}

