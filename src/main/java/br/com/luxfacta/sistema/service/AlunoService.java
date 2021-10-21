package br.com.luxfacta.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import br.com.luxfacta.sistema.domain.Aluno;
import br.com.luxfacta.sistema.domain.Disciplina;
import br.com.luxfacta.sistema.domain.enums.Perfil;
import br.com.luxfacta.sistema.dto.AlunoDTO;
import br.com.luxfacta.sistema.dto.AlunoNewDTO;
import br.com.luxfacta.sistema.exception.AuthorizationException;
import br.com.luxfacta.sistema.exception.DataIntegrityException;
import br.com.luxfacta.sistema.exception.ObjectNotFoundException;
import br.com.luxfacta.sistema.repository.AlunoRepository;
import br.com.luxfacta.sistema.security.UserSS;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private DisciplinaService disciplinaService;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	public Aluno find(Integer id) {
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.PROF) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Aluno aluno = alunoRepository.findById(id)
									 .orElseThrow(
											 () -> new ObjectNotFoundException(
													 "Objeto não encontrado! Id: " 
											 + id + ", Tipo: " + Aluno.class.getName()));
		return aluno;
	}
	
	public List<Aluno> findAll(){
		return alunoRepository.findAll();
	}
	
	public Aluno save(Aluno obj) {
		obj.setId(null);
		emailService.sendSignupEmailConfirmationAluno(obj);
		return alunoRepository.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			alunoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um aluno");
		}
	}
	
	public Aluno update(Aluno obj) {
		Aluno newAluno = find(obj.getId());
		updateData(newAluno, obj);
		return alunoRepository.save(newAluno);
	}
	
	public Aluno updateSomeParams(Aluno obj) {
		Aluno newObj = find(obj.getId());
		updateDataSomeParams(newObj, obj);
		return alunoRepository.save(newObj);
	}
	
	public Aluno fromDTO(AlunoNewDTO objDto) {
		return new Aluno(
					objDto.getId(),
					objDto.getNome(),
					objDto.getEmail(),
					pe.encode(objDto.getSenha()));
	}
	
	public Aluno fromDTO(AlunoDTO objDto) {
		return new Aluno(objDto.getId(),
						 objDto.getNome(),
						 objDto.getEmail());
	}
	
	public void updateData(Aluno newAluno, Aluno aluno) {
		newAluno.setDisciplinas(aluno.getDisciplinas());
	}
	
	public void updateDataSomeParams(Aluno newObj, Aluno obj) {
		if(obj.getNome() != null) {
			newObj.setNome(obj.getNome());
		}
		if(obj.getEmail() != null) {
			newObj.setEmail(obj.getEmail());
		}
		if(obj.getSenha() != null) {
			newObj.setSenha(obj.getSenha());
		}
	}
	
	public Aluno addDisciplina(Integer id_aluno, Integer id_disciplina) {
		Aluno obj = find(id_aluno);
		Disciplina disciplina = disciplinaService.find(id_disciplina);
		obj.getDisciplinas().add(disciplina);
		return updateSomeParams(obj);
	}
}
