package com.mbronshteyn.gameserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.host}")
    String host;
    @Value("${spring.mail.username}")
    String username;
    @Value("${spring.mail.password}")
    String password;
    @Value("${spring.mail.smtp.auth}")
    String auth;
    @Value("${spring.mail.port}")
    String port;
    @Value("${spring.mail.smtp.starttls.enable}")
    String enable;
    @Value("${spring.mail.smtp.fallback}")
    String fallback;
    @Value("${spring.mail.smtp.ssl.enable}")
    String ssl;

    @Bean
    public JavaMailSender javaMailSender()
    {
        JavaMailSenderImpl msender=new JavaMailSenderImpl();
        Properties mailProperties=new Properties();
        mailProperties.put("mail.smtp.auth",auth);
        mailProperties.put("mail.smtp.ssl.enable",ssl);
        mailProperties.put("mail.smtp.fallback",fallback);
        mailProperties.put("mail.smtp.starttls.enable",enable);
        msender.setJavaMailProperties(mailProperties);
        msender.setHost(host);
        msender.setPort(Integer.parseInt(port));
        msender.setUsername(username);
        msender.setPassword(password);
        return msender;
    }

}
