package com.crud.theatre.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="stage_copy_id")
    private StageCopy stageCopy;

    public SpectacleDate(@NotNull LocalDateTime date, Spectacle spectacle, Stage stage) {
        this.date = date;
        this.spectacle = spectacle;
        this.stage = stage;
    }
}
