package com.crud.theatre.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "spectacle")
public class Spectacle {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;

    @OneToMany(
            targetEntity = SpectacleDate.class,
            mappedBy = "spectacle",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<SpectacleDate> spectacleDates = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "join_actor_spectacle",
            joinColumns = {@JoinColumn(name = "spectacle_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id", referencedColumnName = "id")}
    )
    private List<Actor> cast = new ArrayList<>();

    //    )
//    @OneToMany(
//            targetEntity = Actor.class,
//            mappedBy = "spectacle",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER)

    public Spectacle(String name, Stage stage) {
        this.name = name;
        this.stage = stage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spectacle spectacle = (Spectacle) o;
        return Objects.equals(id, spectacle.id) &&
                Objects.equals(name, spectacle.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
