package br.com.luxfacta.sistema.rest.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.luxfacta.sistema.domain.Aluno;
import br.com.luxfacta.sistema.dto.AlunoDTO;
import br.com.luxfacta.sistema.dto.AlunoNewDTO;
import br.com.luxfacta.sistema.service.AlunoService;

@RestController
@RequestMapping(value = "/alunos")
public class AlunoController {

	@Autowired
	private AlunoService alunoService;
	
	@GetMapping("/{id}")
	public ResponseEntity<AlunoDTO>findById(@PathVariable Integer id){
		Aluno aluno = alunoService.find(id);
		return ResponseEntity.ok().body(new AlunoDTO(aluno));
	}
	
	// Retorna todos os alunos e suas informações sem as disciplinas
	@GetMapping
	public ResponseEntity<List<AlunoDTO>> findAllAlunos(){
		List<Aluno> list = alunoService.findAll();
		List<AlunoDTO> listDto = list
									.stream()
									.map(x -> new AlunoDTO(x))
									.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	// Retorna todos os alunos com suas informções pessoais e das disciplinas
	@GetMapping("/disc")
	public ResponseEntity<List<Aluno>> findAllAlunosDisc(){
		List<Aluno> list = alunoService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping
	public ResponseEntity<Void> Insert(@RequestBody AlunoNewDTO objDto){
		Aluno aluno = alunoService.fromDTO(objDto);
		aluno = alunoService.save(aluno);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("{/id}")
				.buildAndExpand(aluno.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();	
		
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateSomeParams(@RequestBody AlunoDTO objDto,
												 @PathVariable Integer id){
		Aluno obj = alunoService.fromDTO(objDto);
		obj.setId(id);
		obj = alunoService.updateSomeParams(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id){
		alunoService.delete(id);;
		return ResponseEntity.noContent().build();
	}
}
