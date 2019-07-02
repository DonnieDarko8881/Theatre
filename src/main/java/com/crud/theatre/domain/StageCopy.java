package com.crud.theatre.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "stage_copy")
public class StageCopy {

    @GeneratedValue
    @Column(name = "id")
    @Id
    private long id;

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

    @Column(name = "spectacle_price_PLN")
    private BigDecimal spectaclePricePLN;

    public StageCopy(Stage stage, Set<Seats> seats, SpectacleDate spectacleDate, BigDecimal spectaclePricePLN) {
        this.stage = stage;
        this.seats = seats;
        this.spectacleDate = spectacleDate;
        this.spectaclePricePLN = spectaclePricePLN;
    }
}
