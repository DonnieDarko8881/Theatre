package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity(name = "seats")
public class Seats {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "number")
    private int number;

    @ManyToOne
    @JoinColumn(name = "stage_copy_id")
    private StageCopy stageCopy;

    @Column(name = "status")
    private String status;

    public Seats(int number, StageCopy stageCopy, String status) {
        this.number = number;
        this.stageCopy = stageCopy;
        this.status = status;
    }
}
