package com.rng.apirng.services;

import com.rng.apirng.domain.Cliente;
import com.rng.apirng.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido pedido);

    void sendEmail(SimpleMailMessage simpleMailMessage);

//    void sendOrderConfirmationHtmlEmail(Pedido pedido);
//
//    void sendHtmlEmail(MimeMessage mimeMessage);

    void sendNewPasswordEmail(Cliente cliente, String newPass);
}