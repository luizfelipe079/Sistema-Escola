package br.com.luxfacta.sistema.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.luxfacta.sistema.domain.Aluno;
import br.com.luxfacta.sistema.domain.Professor;
import br.com.luxfacta.sistema.exception.ObjectNotFoundException;
import br.com.luxfacta.sistema.repository.AlunoRepository;
import br.com.luxfacta.sistema.repository.ProfessorRepository;


@Service
public class AuthService {

	@Autowired
	private AlunoRepository alunoRepo;
	
	@Autowired
	private ProfessorRepository professorRepo;
	
	@Autowired 
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	
	public void sendNewPassword(String email) {
		
		Aluno aluno = alunoRepo.findByEmail(email);
		
		Professor professor = professorRepo.findByEmail(email);

		if(aluno == null && professor == null) 
			throw new ObjectNotFoundException("Email não encontrado");
		
		if(aluno != null) {
		
			String newPass = newPassword();
			aluno.setSenha(bCryptPasswordEncoder.encode(newPass));
			
			alunoRepo.save(aluno);
			emailService.sendNewPasswordEmailAluno(aluno, newPass);
			
		}
		
		if( professor != null ) {
			
			String newPass = newPassword();
			professor.setSenha(bCryptPasswordEncoder.encode(newPass));
			
			professorRepo.save(professor);
			emailService.sendNewPasswordEmailProfessor(professor, newPass);
		}
		
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if(opt == 0) { //gera um digito
			return (char) (rand.nextInt(10) + 48);
		} else if(opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		} else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
	
	
	
}
