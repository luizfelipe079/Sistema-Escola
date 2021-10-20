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

import br.com.luxfacta.sistema.domain.Disciplina;
import br.com.luxfacta.sistema.dto.DisciplinaDTO;
import br.com.luxfacta.sistema.service.DisciplinaService;

@RestController
@RequestMapping(value = "/disciplinas")
public class DisciplinaController {

	@Autowired
	private DisciplinaService disciplinaService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Disciplina>findById(@PathVariable Integer id){
		Disciplina disciplina = disciplinaService.find(id);
		return ResponseEntity.ok().body(disciplina);
	}
	
	@GetMapping
	public ResponseEntity<List<DisciplinaDTO>> findAllDisciplinasDisc(){
		List<Disciplina> list = disciplinaService.findAll();
		List<DisciplinaDTO> listDto = list
				.stream()
				.map(x -> new DisciplinaDTO(x))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@PostMapping
	public ResponseEntity<Void> Insert(@RequestBody DisciplinaDTO objDto){
		Disciplina disciplina = disciplinaService.fromDTO(objDto);
		disciplina = disciplinaService.save(disciplina);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("{/id}")
				.buildAndExpand(disciplina.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();	
		
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateSomeParams(@RequestBody DisciplinaDTO objDto,
												 @PathVariable Integer id){
		Disciplina obj = disciplinaService.fromDTO(objDto);
		obj.setId(id);
		obj = disciplinaService.updateSomeParams(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id){
		disciplinaService.delete(id);;
		return ResponseEntity.noContent().build();
	}
	
}
