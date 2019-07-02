package com.crud.theatre.scheduler;

import com.crud.theatre.clients.apixu.client.ApixuClient;
import com.crud.theatre.domain.Apixu.forecast.forcastDay.Day.Day;
import com.crud.theatre.domain.Mail;
import com.crud.theatre.domain.Reservation;
import com.crud.theatre.service.ReservationService;
import com.crud.theatre.service.mail.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class EmailScheduler {
    private final ReservationService reservationService;
    private final SimpleEmailService simpleEmailService;
    private final ApixuClient apixuClient;

    @Autowired
    public EmailScheduler(ReservationService reservationService, SimpleEmailService simpleEmailService, ApixuClient apixuClient) {
        this.reservationService = reservationService;
        this.simpleEmailService = simpleEmailService;
        this.apixuClient = apixuClient;
    }

//    @Scheduled(fixedDelay = 20000)
   @Scheduled(cron = "0 0 10 * * *")
    public void sendReminderEmail() {
        Day day = apixuClient.getForecastTomorrow().getForecast().getForecastday().get(1).getDay();
        List<Reservation> reservations = reservationService.findAll();
        reservations.stream()
                .filter(reservation -> {
                    int dayOfYear = reservation.getStageCopy().getSpectacleDate().getDate().getDayOfYear();
                    return dayOfYear - LocalDateTime.now().getDayOfYear() == 1;
                })
                .forEach(reservation -> {
                    simpleEmailService.sendReminder(reservation.getUser(), day,
                            new Mail(reservation.getUser().getMail(), "Reminder", spectacleMessage(reservation)));
                });
    }

    private String spectacleMessage(Reservation reservation) {
        @NotNull LocalDateTime date = reservation.getStageCopy().getSpectacleDate().getDate();
        String spectacleDate = date.toLocalDate().toString();
        String spectacleTime = date.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        String stageName = reservation.getStageCopy().getStage().getName();
        String spectacleName = reservation.getStageCopy().getSpectacleDate().getSpectacle().getName();
        return "we would like to remind you about tomorrow's spectacle which you made reservation: \n" +
                "Date: " + spectacleDate + " " + spectacleTime +
                ". " + spectacleName + " will take place in " + stageName +
                " Seats Number " + reservation.getSeatsNumber() + ". ";
    }
}
