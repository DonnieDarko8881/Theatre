package com.crud.theatre.service.mail;

import com.crud.theatre.clients.fixer.service.FixerService;
import com.crud.theatre.domain.Apixu.forecast.forcastDay.Day.Day;
import com.crud.theatre.domain.Mail;
import com.crud.theatre.domain.User;
import com.crud.theatre.repository.MailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SimpleEmailService {
    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    @Autowired
    private FixerService fixerService;

    @Autowired
    private MailRepository mailRepository;

    public void send(User user, Mail mail) {
        LOGGER.info("Starting email preparation...");
        try {
            javaMailSender.send(createMimeMessage(user, mail));
            mailRepository.save(mail);
            LOGGER.info("Email has been sent.");
        } catch (MailException e) {
            LOGGER.error("Failed to process sending: " + e.getMessage(), e);
        }
    }

    public void sendReservationMail(User user, BigDecimal spectaclePricePln){
        double amountPLN = spectaclePricePln.doubleValue();
        send(user, new Mail(user.getMail(), "reservation", "pay one of the following currencies: \n\n"
                        + spectaclePricePln + " PLN\n\n" +
                        fixerService.getEuroFromPLN(amountPLN) + " EUR\n\n"+
                        fixerService.getGBNFromPLN(amountPLN) + " GBN\n\n"+
                        fixerService.getUSDFromPLN(amountPLN) + " USD")
        );
    }

    public void sendReminder(User user,Day day, Mail mail) {
        LOGGER.info("Starting email preparation...");
        try {
            javaMailSender.send(createReminderMimeMessage(user, day, mail));
            mailRepository.save(mail);
            LOGGER.info("Email has been sent.");
        } catch (MailException e) {
            LOGGER.error("Failed to process sending: " + e.getMessage(), e);
        }
    }

    public MimeMessagePreparator createMimeMessage(final User user, final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildMakeReservationMail(user, mail), true);
        };
    }

    public MimeMessagePreparator createReminderMimeMessage(final User user, final Day day, final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildReminderMail(user,day, mail), true);
        };
    }
}
