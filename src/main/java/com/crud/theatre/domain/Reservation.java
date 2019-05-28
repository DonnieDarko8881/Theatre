package com.crud.theatre.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stage_copy_id")
    private StageCopy stageCopy;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "seats_id")
    private Seats seats;

    @Column(name = "seats_number")
    private int seatsNumber;

    public Reservation(StageCopy stageCopy, Seats seats, int seatsNumber) {
        this.stageCopy = stageCopy;
        this.seats = seats;
        this.seatsNumber = seatsNumber;
    }
}
