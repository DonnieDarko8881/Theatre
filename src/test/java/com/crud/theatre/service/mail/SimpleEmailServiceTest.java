package com.crud.theatre.service.mail;

import com.crud.theatre.clients.fixer.service.FixerService;
import com.crud.theatre.domain.Apixu.forecast.forcastDay.Day.Day;
import com.crud.theatre.domain.Mail;
import com.crud.theatre.domain.StageCopy;
import com.crud.theatre.domain.User;
import com.crud.theatre.repository.MailRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MailRepository mailRepository;

    @Test
    public void shouldSendMail(){
        //Given
        Mail mail = new Mail("test@test.com", "Test", "Test");
        User user = new User("firstName", "lastName", "test@test.com", "password");
        //when
        simpleEmailService.sendReminder(user,any(Day.class),mail);

        //Then
        verify(javaMailSender,times(1)).send(any(MimeMessagePreparator.class));
        verify(mailRepository,times(1)).save(mail);
    }
}