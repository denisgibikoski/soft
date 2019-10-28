package com.example.demo.util;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.demo.model.Mensagem;

@Component
public class Email implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	public JavaMailSender emailSender;
	
	public void envia(Mensagem mensagem) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		
		simpleMailMessage.setFrom(mensagem.getRemetente());
		simpleMailMessage.setTo(mensagem.getDestinatarios().toArray(new String[mensagem.getDestinatarios().size()]));
		simpleMailMessage.setSubject(mensagem.getAssunto());
		simpleMailMessage.setText(mensagem.getCorpo());

		emailSender.send(simpleMailMessage);
	}

}
