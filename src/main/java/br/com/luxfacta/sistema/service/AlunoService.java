package br.com.luxfacta.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.luxfacta.sistema.domain.Aluno;
import br.com.luxfacta.sistema.dto.AlunoDTO;
import br.com.luxfacta.sistema.dto.AlunoNewDTO;
import br.com.luxfacta.sistema.exception.DataIntegrityException;
import br.com.luxfacta.sistema.exception.ObjectNotFoundException;
import br.com.luxfacta.sistema.repository.AlunoRepository;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepository;
	
	public Aluno find(Integer id) {
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
	
	public Aluno updateSomeParams(Aluno obj) {
		Aluno newObj = alunoRepository.findById(obj.getId()).orElseThrow(
												() -> new ObjectNotFoundException(
														"Objeto não encontrado! Id:" + obj.getId() + ", Tipo: " + 
														 Aluno.class.getName()));
		updateDataSomeParams(newObj, obj);
		return alunoRepository.save(newObj);
	}
	
	public Aluno fromDTO(AlunoNewDTO objDto) {
		return new Aluno(
					objDto.getId(),
					objDto.getNome(),
					objDto.getEmail(),
					objDto.getSenha());
	}
	
	public Aluno fromDTO(AlunoDTO objDto) {
		return new Aluno(objDto.getId(),
						 objDto.getNome(),
						 objDto.getEmail());
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
}
