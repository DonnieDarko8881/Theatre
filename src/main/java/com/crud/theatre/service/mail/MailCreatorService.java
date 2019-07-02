package com.crud.theatre.service.mail;

import com.crud.theatre.clients.fixer.service.FixerService;
import com.crud.theatre.config.AdminConfig;
import com.crud.theatre.domain.Apixu.forecast.forcastDay.Day.Day;
import com.crud.theatre.domain.Mail;
import com.crud.theatre.domain.Seats;
import com.crud.theatre.domain.StageCopy;
import com.crud.theatre.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Qualifier("templateEngine")
    private final TemplateEngine templateEngine;
    private final AdminConfig adminConfig;
    private final FixerService fixerService;

    @Autowired
    public MailCreatorService(TemplateEngine templateEngine, AdminConfig adminConfig, FixerService fixerService) {
        this.templateEngine = templateEngine;
        this.adminConfig = adminConfig;
        this.fixerService = fixerService;
    }



    public String buildMakeReservationMail(User user, StageCopy stageCopy, Seats seats,Mail mail){
        BigDecimal spectaclePricePLN = stageCopy.getSpectaclePricePLN();
        double amountPLN = spectaclePricePLN.doubleValue();


        List<String> currences = new ArrayList<>();
        currences.add(spectaclePricePLN + " PLN");
        currences.add(fixerService.getEuroFromPLN(amountPLN) + " EUR");
        currences.add(fixerService.getGBNFromPLN(amountPLN) + " GBN");
        currences.add(fixerService.getUSDFromPLN(amountPLN) + " USD");

        Context context = new Context();
        context.setVariable("message", mail.getMessage());
        context.setVariable("spectacleInformation", spectacleInformation(stageCopy,seats));
        context.setVariable("currences", currences);
        context.setVariable("goodbye_message", "Have a nice day! ");
        context.setVariable("theatr_url", "https://localhost:8081");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("user", user);
        context.setVariable("footer", adminConfig.getCompanyDetails());
        return templateEngine.process("mail/make-reservation-mail", context);
    }

    public String buildReminderMail(User user,Day day, Mail mail){

        Context context = new Context();
        context.setVariable("message", mail.getMessage());
        context.setVariable("goodbye_message", "Have a nice day! ");
        context.setVariable("theatr_url", "https://localhost:8081");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("user", user);
        context.setVariable("forecast",forecastMessage(day));
        context.setVariable("footer", adminConfig.getCompanyDetails());
        return templateEngine.process("mail/reminder-mail", context);
    }

    private String forecastMessage(Day forecastTomorrow) {
        String conditionText = forecastTomorrow.getCondition().getText();
        return "\nForecast for tomorrow:  " + conditionText + ".\n" +
                "That day average of temperature will be " + forecastTomorrow.getAvgtemp_c() + " °C";
    }

    private String spectacleInformation(StageCopy stageCopy, Seats seats){
        String spectacleDate = stageCopy.getSpectacleDate().getDate().toLocalDate().toString();
        String spectacleTime = stageCopy.getSpectacleDate().getDate().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        String stageName = stageCopy.getStage().getName();
        String spectacleName = stageCopy.getSpectacleDate().getSpectacle().getName();
         return "You have just made reservation for " + spectacleName+ " that will take place "+
         spectacleDate + " about " + spectacleTime + " in " + stageName + ", Seats Number: " + seats.getNumber()+".";
    }
}
