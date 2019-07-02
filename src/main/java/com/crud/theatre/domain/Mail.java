package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Entity(name = "mail")
public class Mail {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "sending_date")
    private LocalDateTime sendingDate;
    @Column(name = "mail_to")
    private String mailTo;
    @Column(name = "to_cc")
    private String toCc;
    @Column(name = "subject")
    private String subject;
    @Column(name ="message")
    private String message;

    public Mail(String mailTo, String subject, String message) {
        this.mailTo = mailTo;
        this.subject = subject;
        this.message = message;
        this.sendingDate = LocalDateTime.now();
    }
}

