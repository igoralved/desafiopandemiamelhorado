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

import igorgroup.desafiopandemia.DTO.EtapaDTO;
import igorgroup.desafiopandemia.form.EtapaForm;
import igorgroup.desafiopandemia.model.Etapa;
import igorgroup.desafiopandemia.repository.EtapaRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/etapas")
public class EtapaController {
	
	private EtapaRepository etaparepository;
	
	@Autowired
	public EtapaController(EtapaRepository er) {
		this.etaparepository = er;
	}
	
	//@ResponseBody
	//@GetMapping
	@ApiOperation(value = "Lista todas as etapas")
	@RequestMapping(value = "/todas", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Stream<EtapaDTO>> listar(){
			List<Etapa> lista = etaparepository.findAll();
			Stream<EtapaDTO> stream = EtapaDTO.converter(lista).stream();
			return new ResponseEntity<Stream<EtapaDTO>>(stream, HttpStatus.OK);
	}
	
	//@PostMapping
	//@Transactional
	@ApiOperation(value = "Cadastra uma etapa")
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<EtapaDTO> cadastrar(@RequestBody @Valid EtapaForm eform, 
		UriComponentsBuilder uriBuilder) {
		Etapa e = eform.converter();
		etaparepository.save(e);
		URI uri = uriBuilder.path("/etapas/{id}").buildAndExpand(e.getId()).toUri();
		return ResponseEntity.created(uri).body(new EtapaDTO(e));
	}
	
	//@GetMapping("/{id}")
	@ApiOperation(value = "Detalha uma etapa por ID")
	@RequestMapping(value = "/detalhar/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<EtapaDTO> detalhar(@PathVariable Long id) {
		Optional<Etapa> eoptional = etaparepository.findById(id);
		if(eoptional.isPresent()) {
			return ResponseEntity.ok(new EtapaDTO(eoptional.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	//@PutMapping("/{id}")
	//@Transactional
	@ApiOperation(value = "Atualiza uma etapa por ID")
	@RequestMapping(value = "/atualizar/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<EtapaDTO> atualizar(@PathVariable Long id,
			@RequestBody @Valid EtapaForm form){
		Optional<Etapa> optional = etaparepository.findById(id);
		if(optional.isPresent()) {
			Etapa e = form.atualizar(id, etaparepository);
			etaparepository.save(e);
			return ResponseEntity.ok(new EtapaDTO(e));
		}return ResponseEntity.notFound().build();
	}
	
	//@DeleteMapping("/{id}")
	//@Transactional
	@ApiOperation(value = "Remove uma etapa por ID")
	@RequestMapping(value = "/remover/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Etapa> optional = etaparepository.findById(id);
		if(optional.isPresent()) {
			etaparepository.deleteById(id);
			return ResponseEntity.ok().build();
		}return ResponseEntity.notFound().build();
	}
	
}
