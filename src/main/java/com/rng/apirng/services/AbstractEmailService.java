package com.rng.apirng.services;

import com.rng.apirng.domain.Cliente;
import com.rng.apirng.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderConfirmationEmail(Pedido pedido) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(pedido);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {

        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setFrom(sender);
        sm.setTo(pedido.getCliente().getEmail());
        sm.setSubject("Pedido confirmado! Código: " + pedido.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(pedido.toString());

        return sm;
    }

    @Override
    public void sendNewPasswordEmail(Cliente cliente, String newPass) {
        SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(cliente.getEmail());
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setSubject("Solicitação de nova senha");
        simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
        simpleMailMessage.setText("Nova senha: " + newPass);

        return simpleMailMessage;
    }

    //    protected String htmlFromTemplatePedido(Pedido pedido) {
//        Context context = new Context();
//        context.setVariable("pedido", pedido);
//        return templateEngine.process("email/confirmacaoPedido", context);
//    }

//    @Override
//    public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
//        try {
//            MimeMessage mm = prepareMimeMessageFromPedido(pedido);
//            sendHtmlEmail(mm);
//        } catch(MessagingException e){
//            this.sendOrderConfirmationEmail(pedido);
//        }
//    }

//    protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
//        mmh.setTo(pedido.getCliente().getEmail());
//        mmh.setFrom(sender);
//        mmh.setSubject("Pedido confirmado! Código: " + pedido.getId());
//        mmh.setSentDate(new Date(System.currentTimeMillis()));
//        mmh.setText(this.htmlFromTemplatePedido(pedido), true);
//
//        return mimeMessage;
//    }
}