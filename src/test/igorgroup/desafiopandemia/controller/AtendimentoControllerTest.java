package igorgroup.desafiopandemia.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import igorgroup.desafiopandemia.model.Atendimento;
import igorgroup.desafiopandemia.repository.AtendimentoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AtendimentoControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@MockBean
	private AtendimentoRepository ar;
	
	@TestConfiguration
	static class Config{
		@Bean
		public RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder().basicAuthentication("igor", "0000");
		}
	}
	
	@Test
	public void testListar() {
		restTemplate = restTemplate.withBasicAuth("1", "1");
		ResponseEntity<String> result = restTemplate.getForEntity("/atendimentos/listar", String.class);
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void testaListarComMock() {
		Atendimento a1 = new Atendimento();
		a1.setDescricao("Atendimento de idosos");
		a1.setId(1L);
		a1.setRelacionado_com_pandemia(true);
		a1.setSem_possibilidade_contagio(true);
		a1.setTempoAtendimento(10);
		
		Atendimento a2 = new Atendimento();
		a2.setDescricao("Atendimento de menores de idade");
		a2.setId(2L);
		a2.setRelacionado_com_pandemia(false);
		a2.setSem_possibilidade_contagio(false);
		a2.setTempoAtendimento(18);
		
		List<Atendimento> lista = new ArrayList<>();
		lista.add(a1);
		lista.add(a2);
		BDDMockito.when(ar.findAll()).thenReturn(lista);
		
		ResponseEntity<String> result = restTemplate.getForEntity("/atendimentos/listar", String.class);
		
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(200);
		
	}
	
	
	@Test
	public void testaCadastro() {
		Atendimento a1 = new Atendimento();
		a1.setId(1L);
		a1.setDescricao("Atendimento de idade média");
		a1.setRelacionado_com_pandemia(true);
		a1.setSem_possibilidade_contagio(false);
		a1.setTempoAtendimento(10);
		
		BDDMockito.when(ar.save(a1)).thenReturn(a1);
		ResponseEntity<Atendimento> result = restTemplate.postForEntity("/atendimentos/cadastrar", a1, Atendimento.class);
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(201);
	}
	
	@Test
	public void testaDetalhar() {
		Atendimento a1 = new Atendimento();
		a1.setId(2L);
		a1.setDescricao("Atendimento de idade pequena");
		a1.setRelacionado_com_pandemia(false);
		a1.setSem_possibilidade_contagio(true);
		a1.setTempoAtendimento(11);
		
		Optional<Atendimento> ao = Optional.of(a1);
		
		BDDMockito.when(ar.findById(a1.getId())).thenReturn(ao);
		ResponseEntity<Atendimento> result = restTemplate.getForEntity("/atendimentos/detalhar/{id}", Atendimento.class, a1.getId());
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void testaDetalharComFracasso() {
		Atendimento a1 = new Atendimento();
		a1.setId(20L);
		a1.setDescricao("Atendimento de idade pequena a média");
		a1.setRelacionado_com_pandemia(true);
		a1.setSem_possibilidade_contagio(false);
		a1.setTempoAtendimento(22);
		
		Optional<Atendimento> ao = Optional.of(a1);
		
		BDDMockito.when(ar.findById(a1.getId())).thenReturn(ao);
		
		ResponseEntity<Atendimento> result = restTemplate.getForEntity("/atendimentos/detalhar/{id}", Atendimento.class, 23L);
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(404);
	}
	
	@Test
	public void testaAtualizacao() {
		Atendimento a1 = new Atendimento();
		a1.setId(21L);
		a1.setDescricao("Atendimento de idade grande");
		a1.setRelacionado_com_pandemia(true);
		a1.setSem_possibilidade_contagio(false);
		a1.setTempoAtendimento(15);
		
		Optional<Atendimento> ao = Optional.of(a1);
		BDDMockito.when(ar.findById(a1.getId())).thenReturn(ao);
		
		Assertions.assertThat(ao).isPresent();
		
		a1.setId(12L);
		a1.setDescricao("Atendimento de idade pequena");
		a1.setRelacionado_com_pandemia(false);
		a1.setSem_possibilidade_contagio(true);
		a1.setTempoAtendimento(51);
		
		ar.save(a1);
		
		BDDMockito.when(ar.findById(a1.getId())).thenReturn(ao);
		
		Assertions.assertThat(ao).isPresent();
		
		
	}
	
	@Test
	public void testaAtualizacaoComFracasso() {
		Optional<Atendimento> ao = ar.findById(200L);
		BDDMockito.when(ar.findById(200L)).thenReturn(ao);
		Assertions.assertThat(ao).isEmpty();
	}
	
	@Test
	public void testeDeleta() {
		Atendimento a1 = new Atendimento();
		
		a1.setId(2L);
		a1.setDescricao("Atendimento de longa duração");
		a1.setRelacionado_com_pandemia(false);
		a1.setSem_possibilidade_contagio(true);
		a1.setTempoAtendimento(50);
		
		ar.save(a1);
		
		BDDMockito.when(ar.findById(a1.getId())).thenReturn(Optional.of(a1));
		Assertions.assertThat(ar.findById(a1.getId())).isPresent();
		
		ar.deleteById(a1.getId());
		
		BDDMockito.when(ar.findById(a1.getId())).thenReturn(Optional.empty());
		Assertions.assertThat(ar.findById(a1.getId())).isNotPresent();
		
		
	}

}
