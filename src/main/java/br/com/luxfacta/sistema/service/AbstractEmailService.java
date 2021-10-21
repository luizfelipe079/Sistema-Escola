package br.com.luxfacta.sistema.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.luxfacta.sistema.domain.Aluno;
import br.com.luxfacta.sistema.domain.Professor;


public abstract class AbstractEmailService implements EmailService{

	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendNewPasswordEmailAluno(Aluno aluno, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmailAluno(aluno, newPass);
		sendEmail(sm);
	}

	private SimpleMailMessage prepareNewPasswordEmailAluno(Aluno aluno, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(aluno.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova Senha ");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}
	
	@Override
	public void sendNewPasswordEmailProfessor(Professor professor, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmailProfessor(professor, newPass);
		sendEmail(sm);
	}

	private SimpleMailMessage prepareNewPasswordEmailProfessor(Professor professor, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(professor.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova Senha ");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}
}
