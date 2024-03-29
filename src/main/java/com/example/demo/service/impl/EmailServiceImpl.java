package com.example.demo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.demo.model.Mensagem;
import com.example.demo.model.Reserva;
import com.example.demo.model.UnidadeMoradia;
import com.example.demo.model.Usuario;
import com.example.demo.model.enums.StatusReserva;
import com.example.demo.service.EmailService;
import com.example.demo.service.UsuarioService;
import com.example.demo.util.Email;

@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private Email email;

	/**
	 * envia Moradia
	 */
	@Override
	@Async
	@EventListener
	public void enviarMoradia(UnidadeMoradia moradia) {

		String corpo = "Ola " + moradia.getUsuario().getNome() + "\n Sua Unidade de Moradia : " + moradia.getUnidade()
				+ "\n O Status :" + moradia.getStatusUnidadeMoradia().getDescricao()
				+ "\n O Sindico tem prazo de 72 horas para validar seu Cadastro, você recebera um e-mail  assim que confirmando. ";

		List<String> emails = new ArrayList<String>();
		emails.add(moradia.getUsuario().getEmail());
		Mensagem mensagem = new Mensagem(emails, "Status do Cadastro da Moradia", corpo);
		email.envia(mensagem);	
	}

	/**
	 * Envia para o Sindico
	 */
	@Async
	@EventListener
	public void enviarSindico(Reserva reserva) {

		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		dt.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

		String dataInicial = dt.format(reserva.getDataInicial());

		String dataFinal = dt.format(reserva.getDataFinal());
		
		String corpo;
		if (reserva.getStatusReserva().equals(StatusReserva.EXCUIDO)) {
			corpo = "Usuario : " + reserva.getUsuario().getNome() + 
					"\n A Reserva para seguinte data : " + dataInicial+ " a " + dataFinal + 
					"\n Com Status :" + reserva.getStatusReserva().getDescricao();
		}else {
			 corpo = "Usuario : " + reserva.getUsuario().getNome() + " , fez uma nova reserva."+ 
					"\n Reserva para seguinte data : " + dataInicial + " a " + dataFinal + 
					"\n O Status :"	+ reserva.getStatusReserva().getDescricao() + 
					"\n Consulte o Sistema por o quanto antes.";
		}

		Usuario sindico = usuarioService.getSindico();
		List<String> emails = new ArrayList<String>();
		emails.add(sindico.getEmail());
		Mensagem mensagem = new Mensagem(emails, reserva.getStatusReserva()+" Status da Reserva : " + reserva.getUsuario().getNome(), corpo);
		email.envia(mensagem);	
	}

	/**
	 * Envia para Usuario
	 */
	@Async
	@EventListener
	public void enviar(Reserva reserva) {
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		dt.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

		String corpo;

		String dataInicial = dt.format(reserva.getDataInicial());

		String dataFinal = dt.format(reserva.getDataFinal());

		if (reserva.getStatusReserva().equals(StatusReserva.EXCUIDO)) {
			corpo = "Ola " + reserva.getUsuario().getNome() + 
					"\n A Reserva para seguinte data : " + dataInicial+ " a " + dataFinal + 
					"\n Com Status :" + reserva.getStatusReserva().getDescricao();
		} else {
			corpo = "Ola " + reserva.getUsuario().getNome() +
					"\n Sua Reserva para seguinte data : " + dataInicial+ " a " + dataFinal + 
					"\n O Status :" + reserva.getStatusReserva().getDescricao()	+ 
					"\n O Sindico tem prazo de 72 horas para validar sua reserva, você recebera um e-mail  assim que confirmando. ";
		}

		List<String> emails = new ArrayList<String>();
		emails.add(reserva.getUsuario().getEmail());
		Mensagem mensagem = new Mensagem(emails, "Status de sua reserva", corpo);
		email.envia(mensagem);	
	}

	/**
	 * Envia para o Sindico
	 */
	@Override
	@Async
	@EventListener
	public void enviarNovoUsuario(Usuario usuario) {

		String corpo = "Existe usuario pendente de : " + usuario.getNome() + 
				"\n Favor Ativar usuario o quanto antes"+ 
				"\n O Status :" + usuario.getStatusUsuario().getDescricao();

		Usuario sindico = usuarioService.getSindico();

		List<String> emails = new ArrayList<String>();
		emails.add(sindico.getEmail());
		Mensagem mensagem = new Mensagem(emails, "Cadastro de Usuario Novo: " + usuario.getNome(), corpo);
		email.envia(mensagem);		
	}

	/**
	 * Envia nova senha de Usuario
	 */
	@Override
	@Async
	public void enviarSenhaNova(Usuario usuario, String senha) {
		
		String corpo = "Caro usuario : " + usuario.getNome() + 
				"\n Usar a seguinte senha : " + senha+ 
				"\n Favor usuario troque sua senha o quanto antes." + 
				"\n O Status :"	+ usuario.getStatusUsuario().getDescricao();

		List<String> emails = new ArrayList<String>();
		emails.add(usuario.getEmail());
		Mensagem mensagem = new Mensagem(emails, "Redefinição de Senmha de Usuario : " + usuario.getNome(), corpo);	
		email.envia(mensagem);		
	}

}
