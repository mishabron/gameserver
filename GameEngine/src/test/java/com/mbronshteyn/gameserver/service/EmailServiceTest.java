package com.mbronshteyn.gameserver.service;

import com.mbronshteyn.gameserver.mail.Mail;
import com.mbronshteyn.gameserver.services.EmailService;
import com.mbronshteyn.gameserver.services.impl.EmailServiceImpl;
import com.mbronshteyn.gameserver.services.impl.GameServiceImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@DataJpaTest
public class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @Autowired
    @InjectMocks
    EmailServiceImpl emailService;

    @Test
    public void testSendEmail(){

        Mail mail = new Mail();
        mail.setFrom("winner@pingo.win");
        mail.setTo("misha_bronshteyn@hotmail.com");
        mail.setSubject("Sending Simple Email with JavaMailSender Example");
        mail.setContent("This tutorial demonstrates how to send a simple email winner");

        emailService.sendEmail(mail);
    }
}
