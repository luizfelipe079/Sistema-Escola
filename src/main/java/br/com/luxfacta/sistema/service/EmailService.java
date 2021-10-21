package br.com.luxfacta.sistema.service;

import org.springframework.mail.SimpleMailMessage;

import br.com.luxfacta.sistema.domain.Aluno;
import br.com.luxfacta.sistema.domain.Professor;


public interface EmailService {

	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmailAluno(Aluno aluno, String newPass);
	
	void sendNewPasswordEmailProfessor(Professor professor, String newPass);
	
	void sendSignupEmailConfirmationAluno(Aluno aluno);
	
	void sendSignupEmailConfirmationProfessor(Professor professor);
}
