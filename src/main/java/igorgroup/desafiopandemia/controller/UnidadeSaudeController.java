package igorgroup.desafiopandemia.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import igorgroup.desafiopandemia.DTO.AtendimentoDTO;
import igorgroup.desafiopandemia.DTO.UnidadeSaudeDTO;
import igorgroup.desafiopandemia.form.UnidadeSaudeForm;
import igorgroup.desafiopandemia.model.Atendimento;
import igorgroup.desafiopandemia.model.UnidadeSaude;
import igorgroup.desafiopandemia.repository.UnidadeSaudeRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/unidades")
@CrossOrigin(origins = "*")
public class UnidadeSaudeController {

	private UnidadeSaudeRepository unidadesauderepository;

	@Autowired
	public UnidadeSaudeController(UnidadeSaudeRepository ur) {
		this.unidadesauderepository = ur;
	}
	
	
	//@GetMapping("/todas")
	//@Transactional
	@ApiOperation(value = "Retorna todas as unidades de saúde")
	@RequestMapping(value = "/todas", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Stream<UnidadeSaudeDTO>> listarTodas(@RequestParam(value="nome", required = false) String nome
			){
		if(nome == null || nome.length() == 0 || nome.equals("QUALQUER")) {
			Stream<UnidadeSaude> stream = unidadesauderepository.findAll().stream();
			return new ResponseEntity<Stream<UnidadeSaudeDTO>>(stream.map(UnidadeSaudeDTO::new), HttpStatus.OK);
		}Stream<UnidadeSaudeDTO> stream = unidadesauderepository.findByNome(nome).stream().map(UnidadeSaudeDTO::new);
		return new ResponseEntity<Stream<UnidadeSaudeDTO>>(stream, HttpStatus.OK);
	}
	
	//@GetMapping("/atendendo")
	//@Transactional
	@ApiOperation(value = "Retorna as unidades de saúde que tem pelo menos um atendimento")
	@RequestMapping(value = "/atendendo", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Stream<UnidadeSaudeDTO>> listarAtendendo(){
			Stream<UnidadeSaudeDTO> stream = unidadesauderepository.findAll().stream().filter(u -> u.getAtendimentos().size() > 0).map(UnidadeSaudeDTO::new);
			return new ResponseEntity<Stream<UnidadeSaudeDTO>>(stream, HttpStatus.OK);
	}
	
	//@GetMapping("/ordenado")
	//@Transactional
	@ApiOperation(value = "Retorna todas as unidades de saúde via ordenação")
	@RequestMapping(value = "/ordenado", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Stream<UnidadeSaudeDTO>> listarOrdenado(@RequestParam(value="sort", defaultValue = "nome") String sort){
			Stream<UnidadeSaudeDTO> stream = unidadesauderepository.findAll(Sort.by(sort)).stream().map(UnidadeSaudeDTO::new);
			return new ResponseEntity<Stream<UnidadeSaudeDTO>>(stream, HttpStatus.OK);
	}
	
	//@GetMapping("/paginado")
	//@Transactional
	@ApiOperation(value = "Retorna todas as unidades de saúde usando páginas")
	@RequestMapping(value = "/paginado", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Page<UnidadeSaudeDTO>> listarPaginado(@RequestParam(value="limit", defaultValue="0") Integer limit,
			@RequestParam(value="offset", defaultValue="1") Integer offset,
			@RequestParam(value="asc", defaultValue="true") Boolean asc, 
			@RequestParam(value="campo", defaultValue="nome") String fieldOrderBy){
			PageRequest pageable = PageRequest.of(limit, offset, asc ? Direction.ASC : Direction.DESC, fieldOrderBy);
			Page<UnidadeSaudeDTO> pagina = unidadesauderepository.findAll(pageable).map(UnidadeSaudeDTO::new);
			return new ResponseEntity<Page<UnidadeSaudeDTO>>(pagina, HttpStatus.OK);
	}
	
	//@RequestMapping("/num_pacientes_crescente")
	//@GetMapping
	@ApiOperation(value = "Retorna todas as unidades de saúde com número de pacientes crescente em uma semana")
	@RequestMapping(value = "/num_pacientes_crescente", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Stream<UnidadeSaudeDTO>> listarComNumeroDePacientesCrescente(@RequestParam(value = "data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data){
		List<UnidadeSaude> listaatual = unidadesauderepository.findByData(data);
		if(listaatual == null || listaatual.size() == 0) {
			return ResponseEntity.noContent().build(); 
		}
		List<UnidadeSaude> listapassada = unidadesauderepository.findByData(data.minusWeeks(1));
		if(listapassada == null || listapassada.size() == 0) {
			return ResponseEntity.noContent().build(); 
		}
		Stream<UnidadeSaudeDTO> classificadas = listaatual.stream()
				.filter(u -> find(listapassada, u.getNome()) != null)
				.filter(u -> growing(u, listapassada))
				.map(UnidadeSaudeDTO::new);
		return new ResponseEntity<Stream<UnidadeSaudeDTO>>(classificadas, HttpStatus.OK);
	}
	
	private boolean growing(UnidadeSaude u, List<UnidadeSaude> lista) {
		for(UnidadeSaude u2 : lista) {
			if(u2.getNome().equals(u.getNome())) {
				if(u.getNumeroPacientes() > u2.getNumeroPacientes()) {
					return true;
				}
			}
		}
		return false;
	}
	
	private UnidadeSaude find(List<UnidadeSaude> lista, String nome) {
		for(UnidadeSaude u : lista) {
			if(u.getNome().equals(nome)) {
				return u;
			}
		}
		return null;
	}
	
	//@RequestMapping("/atendimentos_minimos")
	//@GetMapping
	@ApiOperation(value = "Retorna todas as unidades de saúde com seus atendimentos mínimos")
	@RequestMapping(value = "/atendimentos_minimos", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<HashMap<UnidadeSaudeDTO, Double>> listarAtendimentosMinimos(@RequestParam(value = "data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data){
		List<UnidadeSaude> listasemanal = unidadesauderepository.findByData(data);
		HashMap<UnidadeSaudeDTO, Double> result = new HashMap<>();
		for(UnidadeSaude u : listasemanal) {
			DoubleSummaryStatistics d = u.getAtendimentos().stream().mapToDouble(Atendimento::getTempoAtendimento).summaryStatistics();
			result.put(new UnidadeSaudeDTO(u), d.getMin());
		}return new ResponseEntity<HashMap<UnidadeSaudeDTO, Double>>(result, HttpStatus.OK);
	}
	
	//@RequestMapping("/atendimentos_maximos")
	//@GetMapping
	@ApiOperation(value = "Retorna todas as unidades de saúde com seus atendimentos máximos")
	@RequestMapping(value = "/atendimentos_maximos", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<HashMap<UnidadeSaudeDTO, Double>> listarAtendimentosMaximos(@RequestParam(value = "data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data){
		List<UnidadeSaude> listasemanal = unidadesauderepository.findByData(data);
		HashMap<UnidadeSaudeDTO, Double> result = new HashMap<>();
		for(UnidadeSaude u : listasemanal) {
			DoubleSummaryStatistics d = u.getAtendimentos().stream().mapToDouble(Atendimento::getTempoAtendimento).summaryStatistics();
			result.put(new UnidadeSaudeDTO(u), d.getMax());
		}return new ResponseEntity<HashMap<UnidadeSaudeDTO, Double>>(result, HttpStatus.OK);
	}
	
	//@RequestMapping("/atendimentos_medios")
	//@GetMapping
	@ApiOperation(value = "Retorna todas as unidades de saúde com seus atendimentos médios")
	@RequestMapping(value = "/atendimentos_medios", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<HashMap<UnidadeSaudeDTO, Double>> listarAtendimentosMedios(@RequestParam(value = "data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data){
		List<UnidadeSaude> listasemanal = unidadesauderepository.findByData(data);
		HashMap<UnidadeSaudeDTO, Double> result = new HashMap<>();
		for(UnidadeSaude u : listasemanal) {
			DoubleSummaryStatistics d = u.getAtendimentos().stream().mapToDouble(Atendimento::getTempoAtendimento).summaryStatistics();
			result.put(new UnidadeSaudeDTO(u), d.getAverage());
		}return new ResponseEntity<HashMap<UnidadeSaudeDTO, Double>>(result, HttpStatus.OK);
	}
	
	//@PostMapping
	//@Transactional
	@ApiOperation(value = "Cadastra uma unidade de saúde")
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<UnidadeSaudeDTO> cadastrar(@RequestBody UnidadeSaudeForm uform, 
		UriComponentsBuilder uriBuilder) {
		UnidadeSaude u = uform.converter();
		unidadesauderepository.save(u);
		URI uri = uriBuilder.path("/unidades/{id}").buildAndExpand(u.getId()).toUri();
		return ResponseEntity.created(uri).body(new UnidadeSaudeDTO(u));
	}
	
	//@GetMapping("/{id}")
	@ApiOperation(value = "Detalha uma unidade de saúde por ID")
	@RequestMapping(value = "/detalhar/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<UnidadeSaudeDTO> detalhar(@PathVariable Long id) {
		Optional<UnidadeSaude> uoptional = unidadesauderepository.findById(id);
		if(uoptional.isPresent()) {
			return ResponseEntity.ok(new UnidadeSaudeDTO(uoptional.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	//@PutMapping("/{id}")
	//@Transactional
	@ApiOperation(value = "Atualiza uma unidade de saúde por ID")
	@RequestMapping(value = "/atualizar/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<UnidadeSaudeDTO> atualizar(@PathVariable Long id,
			@RequestBody @Valid UnidadeSaudeForm form){
		Optional<UnidadeSaude> optional = unidadesauderepository.findById(id);
		if(optional.isPresent()) {
			UnidadeSaude u = form.atualizar(id, unidadesauderepository);
			unidadesauderepository.save(u);
			return ResponseEntity.ok(new UnidadeSaudeDTO(u));
		}return ResponseEntity.notFound().build();
	}
	
	//@DeleteMapping("/{id}")
	//@Transactional
	@ApiOperation(value = "Remove uma unidade de saúde por ID")
	@RequestMapping(value = "/remover/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<UnidadeSaude> optional = unidadesauderepository.findById(id);
		if(optional.isPresent()) {
			unidadesauderepository.deleteById(id);
			return ResponseEntity.ok().build();
		}return ResponseEntity.notFound().build();
	}
	
	//@GetMapping("/{id}/atendimentos")
	//@Transactional
	@ApiOperation(value = "Retorna os atendimentos de uma unidade de saúde passada por ID")
	@RequestMapping(value = "/{id}/atendimentos", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Stream<AtendimentoDTO>> verAtendimentosDe(@PathVariable Long id) {
		Optional<UnidadeSaude> usoptional = unidadesauderepository.findById(id);
		if(usoptional.isPresent()) {
			Stream<Atendimento> stream = usoptional.get().getAtendimentos().stream();
			Stream<AtendimentoDTO> result = AtendimentoDTO.converter(stream);
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	}
	
}
