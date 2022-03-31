package igorgroup.desafiopandemia.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import igorgroup.desafiopandemia.DTO.TesteDTO;
import igorgroup.desafiopandemia.form.TesteForm;
import igorgroup.desafiopandemia.model.Teste;
import igorgroup.desafiopandemia.repository.TesteRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/testes")
public class TesteController {

	
	private TesteRepository testerepository;
	
	@Autowired
	public TesteController(TesteRepository tr) {
		this.testerepository = tr;
	}
	
	
	//@GetMapping
	//@ResponseBody
	@ApiOperation(value = "Lista todos os testes")
	@RequestMapping(value = "/todos", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Stream<TesteDTO>> listar(){
			List<Teste> lista = testerepository.findAll();
			Stream<TesteDTO> stream = TesteDTO.converter(lista).stream();
			return new ResponseEntity<Stream<TesteDTO>>(stream, HttpStatus.OK);
	}
	
	//@PostMapping
	//@Transactional
	@ApiOperation(value = "Cadastra um teste")
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<TesteDTO> cadastrar(@RequestBody @Valid TesteForm eform, 
		UriComponentsBuilder uriBuilder) {
		Teste t = eform.converter();
		testerepository.save(t);
		URI uri = uriBuilder.path("/testes/{id}").buildAndExpand(t.getId()).toUri();
		return ResponseEntity.created(uri).body(new TesteDTO(t));
	}
	
	//@GetMapping("/{id}")
	@ApiOperation(value = "Detalha um teste por ID")
	@RequestMapping(value = "/detalhar/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<TesteDTO> detalhar(@PathVariable Long id) {
		Optional<Teste> eoptional = testerepository.findById(id);
		if(eoptional.isPresent()) {
			return ResponseEntity.ok(new TesteDTO(eoptional.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	//@PutMapping("/{id}")
	//@Transactional
	@ApiOperation(value = "Atualiza um teste por ID")
	@RequestMapping(value = "/atualizar/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<TesteDTO> atualizar(@PathVariable Long id,
			@RequestBody @Valid TesteForm form){
		Optional<Teste> optional = testerepository.findById(id);
		if(optional.isPresent()) {
			Teste t = form.atualizar(id, testerepository);
			testerepository.save(t);
			return ResponseEntity.ok(new TesteDTO(t));
		}return ResponseEntity.notFound().build();
	}
	
	//@DeleteMapping("/{id}")
	//@Transactional
	@ApiOperation(value = "Remove um teste por ID")
	@RequestMapping(value = "/remover/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Teste> optional = testerepository.findById(id);
		if(optional.isPresent()) {
			testerepository.deleteById(id);
			return ResponseEntity.ok().build();
		}return ResponseEntity.notFound().build();
	}
	
}
