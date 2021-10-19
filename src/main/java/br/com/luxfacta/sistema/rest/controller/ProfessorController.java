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

import br.com.luxfacta.sistema.domain.Professor;
import br.com.luxfacta.sistema.dto.ProfessorDTO;
import br.com.luxfacta.sistema.dto.ProfessorNewDTO;
import br.com.luxfacta.sistema.service.ProfessorService;

@RestController
@RequestMapping(value = "/professores")
public class ProfessorController {

	@Autowired
	private ProfessorService professorService;
	
	@GetMapping("/{id}")
	public ResponseEntity<ProfessorDTO>findById(@PathVariable Integer id){
		Professor professor = professorService.find(id);
		return ResponseEntity.ok().body(new ProfessorDTO(professor));
	}
	
	@GetMapping
	public ResponseEntity<List<ProfessorDTO>> findAllProfessors(){
		List<Professor> list = professorService.findAll();
		List<ProfessorDTO> listDto = list
									.stream()
									.map(x -> new ProfessorDTO(x))
									.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	
	@PostMapping
	public ResponseEntity<Void> Insert(@RequestBody ProfessorNewDTO objDto){
		Professor professor = professorService.fromDTO(objDto);
		professor = professorService.save(professor);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("{/id}")
				.buildAndExpand(professor.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();	
		
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateSomeParams(@RequestBody ProfessorNewDTO objDto,
												 @PathVariable Integer id){
		Professor obj = professorService.fromDTO(objDto);
		obj.setId(id);
		obj = professorService.updateSomeParams(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id){
		professorService.delete(id);;
		return ResponseEntity.noContent().build();
	}
}
