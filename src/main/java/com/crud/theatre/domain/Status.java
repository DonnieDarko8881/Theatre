package com.crud.theatre.domain;

public enum Status {
    RESERVER("RESERVED"), FREE("FREE"), PAYED("PAYED");

    private String status;

    Status(String status) {
        this.status = status;
    }
}
