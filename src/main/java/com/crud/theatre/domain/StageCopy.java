package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "stage_copy")
public class StageCopy {

    @GeneratedValue
    @Column(name = "id")
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;

    @OneToMany(
            targetEntity = Seats.class,
            mappedBy = "stageCopy",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<Seats> seats = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "spectacle_date_id")
    private SpectacleDate spectacleDate;

    @OneToMany(
            targetEntity = Reservation.class,
            mappedBy = "stageCopy",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<Reservation> reservations = new HashSet<>();

    public StageCopy(Stage stage, Set<Seats> seats, SpectacleDate spectacleDate) {
        this.stage = stage;
        this.seats = seats;
        this.spectacleDate = spectacleDate;
    }
}
