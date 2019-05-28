package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "spectacle_date")
public class SpectacleDate {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "date")
    @NotNull
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "spectacle_id")
    private Spectacle spectacle;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;

    public SpectacleDate(@NotNull LocalDateTime date, Spectacle spectacle, Stage stage) {
        this.date = date;
        this.spectacle = spectacle;
        this.stage = stage;
    }
}
