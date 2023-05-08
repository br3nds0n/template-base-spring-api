package br.com.template.base.services.impl;

import br.com.template.base.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void enviarEmail(String para, String conteudo, String texto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("templateBase@gmail.com");
        message.setTo(para);
        message.setSubject(conteudo);
        message.setText(texto);
        emailSender.send(message);

        log.info("Email enviado para: " + para);
    }
}
