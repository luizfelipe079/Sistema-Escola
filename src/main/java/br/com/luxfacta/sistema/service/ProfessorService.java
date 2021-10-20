package br.com.luxfacta.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.luxfacta.sistema.domain.Professor;
import br.com.luxfacta.sistema.domain.enums.Perfil;
import br.com.luxfacta.sistema.dto.ProfessorDTO;
import br.com.luxfacta.sistema.dto.ProfessorNewDTO;
import br.com.luxfacta.sistema.exception.AuthorizationException;
import br.com.luxfacta.sistema.exception.DataIntegrityException;
import br.com.luxfacta.sistema.exception.ObjectNotFoundException;
import br.com.luxfacta.sistema.repository.ProfessorRepository;
import br.com.luxfacta.sistema.security.UserSS;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public Professor find(Integer id) {
		Professor professor = professorRepository.findById(id)
									 .orElseThrow(
											 () -> new ObjectNotFoundException(
													 "Objeto não encontrado! Id: " 
											 + id + ", Tipo: " + Professor.class.getName()));
		
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.PROF) && !professor.getId().equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		return professor;
	}
	
	public List<Professor> findAll(){
		return professorRepository.findAll();
	}
	
	public Professor save(Professor obj) {
		obj.setId(null);
		return professorRepository.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			professorRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um professor");
		}
	}
	
	public Professor updateSomeParams(Professor obj) {
		Professor newObj = professorRepository.findById(obj.getId()).orElseThrow(
												() -> new ObjectNotFoundException(
														"Objeto não encontrado! Id:" + obj.getId() + ", Tipo: " + 
														 Professor.class.getName()));
		updateDataSomeParams(newObj, obj);
		return professorRepository.save(newObj);
	}
	
	public Professor fromDTO(ProfessorNewDTO objDto) {
		return new Professor(
					objDto.getId(),
					objDto.getNome(),
					objDto.getEmail(),
					pe.encode(objDto.getSenha()));
	}
	
//	public Professor fromDTO(ProfessorDTO objDto) {
//		return new Professor(objDto.getId(),
//						 	 objDto.getNome(),
//						 	 objDto.getEmail());
//	}
	
	public Professor fromDTO(ProfessorDTO objDto) {
		return new Professor(objDto.getId(),
							 objDto.getNome(),
							 objDto.getEmail(),
							 pe.encode(objDto.getSenha()));
	}
	
	public void updateDataSomeParams(Professor newObj, Professor obj) {
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
}
