package br.com.luxfacta.sistema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.luxfacta.sistema.domain.Aluno;
import br.com.luxfacta.sistema.domain.Professor;
import br.com.luxfacta.sistema.repository.AlunoRepository;
import br.com.luxfacta.sistema.repository.ProfessorRepository;
import br.com.luxfacta.sistema.security.UserSS;



@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private ProfessorRepository professorRepo;
	
	@Autowired
	private AlunoRepository alunoRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Professor prof = professorRepo.findByEmail(email);
		Aluno aluno = alunoRepo.findByEmail(email);
		
		if(prof == null && aluno == null) {
			throw new UsernameNotFoundException(email);
		}
		
		if(prof != null) {
			return new UserSS(prof.getId(), prof.getEmail(), prof.getSenha(), prof.getPerfil());

		} else {
			return new UserSS(aluno.getId(), aluno.getEmail(),aluno.getSenha(),aluno.getPerfil());
		}
	}
}
