package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "login_information")
public class LoginInformation {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "is_success")
    private boolean success;
    @Column(name = "message")
    private String message;

    public LoginInformation(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
