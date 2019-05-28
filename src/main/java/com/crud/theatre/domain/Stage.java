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
@AllArgsConstructor
@Entity(name = "stage")
public class Stage {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "seats_amount")
    private Integer seatsAmount;

    @OneToMany(
            targetEntity = Spectacle.class,
            mappedBy = "stage",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<Spectacle> spectacles = new ArrayList<>();

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
}
