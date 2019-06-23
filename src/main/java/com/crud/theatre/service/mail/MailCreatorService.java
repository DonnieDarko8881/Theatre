package com.crud.theatre.service.mail;

import com.crud.theatre.domain.Apixu.forecast.forcastDay.Day.Day;
import com.crud.theatre.domain.Mail;
import com.crud.theatre.domain.User;
import com.crud.theatre.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Qualifier("templateEngine")
    private final TemplateEngine templateEngine;
    private final UserService userService;

    @Autowired
    public MailCreatorService(TemplateEngine templateEngine, UserService userService) {
        this.templateEngine = templateEngine;
        this.userService = userService;
    }

    public String buildMakeReservationMail(User user, Mail mail){

        Context context = new Context();
        context.setVariable("message", mail.getMessage());
        context.setVariable("goodbye_message", "Have a nice day! ");
        context.setVariable("theatr_url", "https://localhost:8081");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("user", user);
        context.setVariable("footer", "tutaj będzie telefon i mail kontaktowy do teatru");
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
        context.setVariable("footer", "tutaj będzie telefon i mail kontaktowy do teatru");
        return templateEngine.process("mail/reminder-mail", context);
    }

    private String forecastMessage(Day forecastTomorrow) {
        String conditionText = forecastTomorrow.getCondition().getText();
        return "\nForecast for tomorrow: " + conditionText + "\n" +
                "That day average of temperature will be " + forecastTomorrow.getAvgtemp_c() + " °C";
    }
}
