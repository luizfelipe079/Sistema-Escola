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

import br.com.luxfacta.sistema.domain.Nota;
import br.com.luxfacta.sistema.dto.NotaDTO;
import br.com.luxfacta.sistema.service.NotaService;

@RestController
@RequestMapping(value = "/notas")
public class NotaController {

	@Autowired
	private NotaService notaService;
	
	@GetMapping("/{id}")
	public ResponseEntity<NotaDTO>findById(@PathVariable Integer id){
		Nota nota = notaService.find(id);
		return ResponseEntity.ok().body(new NotaDTO(nota));
	}
	
	@GetMapping
	public ResponseEntity<List<NotaDTO>> findAllNotas(){
		List<Nota> list = notaService.findAll();
		List<NotaDTO> listDto = list
									.stream()
									.map(x -> new NotaDTO(x))
									.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@PostMapping("adicionar/{id_aluno}/{id_disciplina}")
	public ResponseEntity<Void> Insert(@RequestBody NotaDTO objDto,
									   @PathVariable Integer id_aluno,
									   @PathVariable Integer id_disciplina){
		Nota nota = notaService.fromDTO(objDto);
		nota = notaService.addNota(nota, id_aluno, id_disciplina);
		nota = notaService.save(nota);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("{/id}")
				.buildAndExpand(nota.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();	
		
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateSomeParams(@RequestBody NotaDTO objDto,
												 @PathVariable Integer id){
		Nota obj = notaService.fromDTO(objDto);
		obj.setId(id);
		obj = notaService.updateSomeParams(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id){
		notaService.delete(id);;
		return ResponseEntity.noContent().build();
	}
	
}
