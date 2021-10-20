package br.com.luxfacta.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.luxfacta.sistema.domain.Aluno;
import br.com.luxfacta.sistema.domain.Disciplina;
import br.com.luxfacta.sistema.domain.Nota;
import br.com.luxfacta.sistema.domain.enums.Perfil;
import br.com.luxfacta.sistema.dto.NotaDTO;
import br.com.luxfacta.sistema.exception.AuthorizationException;
import br.com.luxfacta.sistema.exception.DataIntegrityException;
import br.com.luxfacta.sistema.exception.ObjectNotFoundException;
import br.com.luxfacta.sistema.repository.NotasRepository;
import br.com.luxfacta.sistema.security.UserSS;

@Service
public class NotaService {

	@Autowired
	private NotasRepository notaRepository;
	
	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private DisciplinaService disciplinaService;
	
	public Nota find(Integer id) {
		
		Nota nota = notaRepository.findById(id)
									 .orElseThrow(
											 () -> new ObjectNotFoundException(
													 "Objeto não encontrado! Id: " 
											 + id + ", Tipo: " + Nota.class.getName()));

		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.PROF) && !nota.getId().equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		return nota;
	}
	
	public List<Nota> findAll(){
		return notaRepository.findAll();
	}
	
	public Nota save(Nota obj) {
		obj.setId(null);
		return notaRepository.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			notaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um nota");
		}
	}
	
	public Nota updateSomeParams(Nota obj) {
		Nota newObj = notaRepository.findById(obj.getId()).orElseThrow(
												() -> new ObjectNotFoundException(
														"Objeto não encontrado! Id:" + obj.getId() + ", Tipo: " + 
														 Nota.class.getName()));
		updateDataSomeParams(newObj, obj);
		return notaRepository.save(newObj);
	}
	
	public Nota fromDTO(NotaDTO objDto) {
		return new Nota(objDto.getNota1(),
						objDto.getNota2());
	}
	
	public void updateDataSomeParams(Nota newObj, Nota obj) {
		if(obj.getNota1() != null) {
			newObj.setNota1(obj.getNota1());
		}
		if(obj.getNota2() != null) {
			newObj.setNota2(obj.getNota2());
		}
	}
	
	public Nota addNota(Nota nota,Integer id_aluno, Integer id_disciplina) {
		Aluno aluno = alunoService.find(id_aluno);
		Disciplina disciplina = disciplinaService.find(id_disciplina);
		
			if(verificarAluno(aluno, disciplina)) {
				nota.setAluno(aluno);
				nota.setDisciplina(disciplina);
			} else {
				throw new ObjectNotFoundException("Aluno não pertence a disciplina");
			}
		return nota;
	}
	
	public boolean verificarAluno(Aluno aluno,Disciplina disciplina) {
		for(Disciplina x : aluno.getDisciplinas()) {
			if(x.equals(disciplina))
				return true;
		}
		return false;
	}
}
