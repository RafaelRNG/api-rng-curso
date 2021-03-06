package com.rng.apirng.services;

import com.rng.apirng.domain.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.util.Date;

public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

//    @Autowired
//    private JavaMailSender javaMailSender;

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        LOG.info("Enviando email...");
        mailSender.send(simpleMailMessage);
        LOG.info("E-mail enviado ");
    }

    //    @Override
//    public void sendHtmlEmail(MimeMessage mimeMessage) {
//        LOG.info("Enviando email...");
//        javaMailSender.send(mimeMessage);
//        LOG.info("E-mail enviado ");
//    }
}