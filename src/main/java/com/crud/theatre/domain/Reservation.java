package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Entity(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "reservation_date")
    @NotNull
    private LocalDateTime reservationDate;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "stage_copy_id")
    private StageCopy stageCopy;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "seats_id")
    private Seats seats;

    @Column(name = "seats_number")
    private int seatsNumber;

    public Reservation(User user, StageCopy stageCopy, Seats seats, int seatsNumber) {
        this.reservationDate = LocalDateTime.now();
        this.user = user;
        this.stageCopy = stageCopy;
        this.seats = seats;
        this.seatsNumber = seatsNumber;
    }

    //do usuniecia

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", reservationDate=" + reservationDate +
                ", user=" + user +
                ", stageCopy=" + stageCopy +
                ", seats=" + seats +
                ", seatsNumber=" + seatsNumber +
                '}';
    }
}
