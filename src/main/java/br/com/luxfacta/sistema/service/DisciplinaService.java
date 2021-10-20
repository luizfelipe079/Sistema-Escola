package br.com.luxfacta.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.luxfacta.sistema.domain.Disciplina;
import br.com.luxfacta.sistema.dto.DisciplinaDTO;
import br.com.luxfacta.sistema.exception.DataIntegrityException;
import br.com.luxfacta.sistema.exception.ObjectNotFoundException;
import br.com.luxfacta.sistema.repository.DisciplinaRepository;

@Service
public class DisciplinaService {
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
//	@Autowired
//	private AlunoRepository alunoRepository;
	
//	@Autowired
//	private NotasRepository notasRepository;
	
	public Disciplina find(Integer id) {
		Disciplina obj = disciplinaRepository.findById(id).orElseThrow(
										() -> new ObjectNotFoundException(
										"Objeto não encontrado! Id:" + id + ", Tipo: " 
										+ Disciplina.class.getName()));
		return obj;
	}
	
	public List<Disciplina> findAll(){
		return disciplinaRepository.findAll();
	}
	
	public Disciplina save(Disciplina obj) {
		obj.setId(null);
		return disciplinaRepository.save(obj);
	}

	public Disciplina fromDTO(DisciplinaDTO objDto) {
		return new Disciplina(objDto.getId(),
							  objDto.getNome());
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			disciplinaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um aluno");
		}
	}
	
	public Disciplina updateSomeParams(Disciplina obj) {
		Disciplina newObj = disciplinaRepository.findById(obj.getId()).orElseThrow(
												() -> new ObjectNotFoundException(
														"Objeto não encontrado! Id:" + obj.getId() + ", Tipo: " + 
														 Disciplina.class.getName()));
		updateDataSomeParams(newObj, obj);
		return disciplinaRepository.save(newObj);
	}
	
	public void updateDataSomeParams(Disciplina newObj, Disciplina obj) {
		if(obj.getId() != null) {
			newObj.setNome(obj.getNome());
		}
		
		if(obj.getNome() != null) {
			newObj.setNome(obj.getNome());
		}
	}
}
