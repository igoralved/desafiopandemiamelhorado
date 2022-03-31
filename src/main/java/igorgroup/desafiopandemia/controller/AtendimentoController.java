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

import igorgroup.desafiopandemia.DTO.AtendimentoDTO;
import igorgroup.desafiopandemia.DTO.EtapaDTO;
import igorgroup.desafiopandemia.DTO.TesteDTO;
import igorgroup.desafiopandemia.form.AtendimentoForm;
import igorgroup.desafiopandemia.model.Atendimento;
import igorgroup.desafiopandemia.model.Etapa;
import igorgroup.desafiopandemia.model.Teste;
import igorgroup.desafiopandemia.repository.AtendimentoRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;


@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {
	
	private AtendimentoRepository atendimentorepository;
	
	@Autowired
	public AtendimentoController(AtendimentoRepository ar) {
		this.atendimentorepository = ar;
	}
	
	//@GetMapping
	//@ResponseBody
	@ApiOperation(value = "Retorna todos os atendimentos")
	@RequestMapping(value = "/listar", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Stream<AtendimentoDTO>> listar(){
		List<Atendimento> lista = atendimentorepository.findAll();
		Stream<AtendimentoDTO> stream = lista.stream().filter(a -> a.getRelacionado_com_pandemia()).map(AtendimentoDTO::new);
		return new ResponseEntity<Stream<AtendimentoDTO>>(stream, HttpStatus.OK);
	}
	
	//@PostMapping
	//@Transactional
	@ApiOperation(value = "Cadastra um atendimento")
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<AtendimentoDTO> cadastrar(@RequestBody @Valid AtendimentoForm aform, 
		UriComponentsBuilder uriBuilder) {
		Atendimento a = aform.converter();
		atendimentorepository.save(a);
		URI uri = uriBuilder.path("/atendimentos/{id}").buildAndExpand(a.getId()).toUri();
		return ResponseEntity.created(uri).body(new AtendimentoDTO(a));
	}
	
	//@GetMapping("/{id}")
	//@Transactional
	@ApiOperation(value = "Detalha um atendimento por ID")
	@RequestMapping(value = "/detalhar/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<AtendimentoDTO> detalhar(@PathVariable Long id) {
		Optional<Atendimento> aoptional = atendimentorepository.findById(id);
		if(aoptional.isPresent()) {
			return ResponseEntity.ok(new AtendimentoDTO(aoptional.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	//@PutMapping("/{id}")
	//@Transactional
	@ApiOperation(value = "Atualiza um atendimento por ID")
	@RequestMapping(value = "/atualizar/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<AtendimentoDTO> atualizar(@PathVariable Long id,
			@RequestBody @Valid AtendimentoForm form){
		Optional<Atendimento> optional = atendimentorepository.findById(id);
		if(optional.isPresent()) {
			Atendimento a = form.atualizar(id, atendimentorepository);
			atendimentorepository.save(a);
			return ResponseEntity.ok(new AtendimentoDTO(a));
		}return ResponseEntity.notFound().build();
	}
	
	//@DeleteMapping("/{id}")
	//@Transactional
	@ApiOperation(value = "Remove um atendimento por ID")
	@RequestMapping(value = "/remover/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Atendimento> optional = atendimentorepository.findById(id);
		if(optional.isPresent()) {
			atendimentorepository.deleteById(id);
			return ResponseEntity.ok().build();
		}return ResponseEntity.notFound().build();
	}
	
	//@GetMapping("/{id}/etapas")
	//@Transactional
	@ApiOperation(value = "Detalha todas as etapas de um atendimento passado por ID")
	@RequestMapping(value = "/{id}/etapas", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<EtapaDTO>> verEtapasDe(@PathVariable Long id) {
		Optional<Atendimento> aoptional = atendimentorepository.findById(id);
		if(aoptional.isPresent()) {
			List<Etapa> lista = aoptional.get().getEtapas();
			List<EtapaDTO> result = EtapaDTO.converter(lista);
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	}

	//@GetMapping("/{id}/testes")
	//@Transactional
	@ApiOperation(value = "Detalha todos os testes de um atendimento passado por ID")
	@RequestMapping(value = "/{id}/testes", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<TesteDTO>> verTestesDe(@PathVariable Long id) {
		Optional<Atendimento> aoptional = atendimentorepository.findById(id);
		if(aoptional.isPresent()) {
			List<Teste> lista = aoptional.get().getTestes();
			List<TesteDTO> result = TesteDTO.converter(lista);
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	}
	
}
