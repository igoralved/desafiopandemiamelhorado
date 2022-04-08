package igorgroup.desafiopandemia.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import igorgroup.desafiopandemia.model.Etapa;
import igorgroup.desafiopandemia.model.Teste;
import igorgroup.desafiopandemia.repository.TesteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TesteControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@MockBean
	private TesteRepository tr;
	
	@TestConfiguration
	static class Config{
		@Bean
		public RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder().basicAuthentication("igor", "0000");
		}
	}
	
	
	
	@Test
	public void test() {
		restTemplate = restTemplate.withBasicAuth("1", "1");
		ResponseEntity<String> result = restTemplate.getForEntity("/testes/todos", String.class);
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void testeComMock() {
		//Teste 1
		Teste t1 = new Teste();
		t1.setId(1L);
		t1.setNumero(1);
		t1.setResultado("ok");
		//Teste 2
		Teste t2 = new Teste();
		t2.setId(2L);
		t2.setNumero(2);
		t2.setResultado("muito ok");
		
		List<Teste> lista = new ArrayList<>();
		lista.add(t1);
		lista.add(t2);
		
		BDDMockito.when(tr.findAll()).thenReturn(lista);
		ResponseEntity<String> result = restTemplate.getForEntity("/testes/todos", String.class);
		
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void testaTesteUnicoComMock() {
		Teste t1 = new Teste();
		t1.setId(1L);
		t1.setNumero(1);
		t1.setResultado("bom demais");
		
		Optional<Teste> t = Optional.of(t1);
		BDDMockito.when(tr.findById(t1.getId())).thenReturn(t);
		
		ResponseEntity<Teste> result = restTemplate.getForEntity("/testes/detalhar/{id}", Teste.class, t1.getId());
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void testaDetalheComMockFracasso() {
		//etapa 1
		Teste t = new Teste();
		t.setId(1L);
		t.setNumero(1);
		t.setResultado("Bem bom");
			
		Optional<Teste> to = Optional.of(t);
				
		BDDMockito.when(tr.findById(t.getId())).thenReturn(to);
				
		ResponseEntity<Teste> result = restTemplate.getForEntity("/testes/detalhar/{id}", Teste.class, 2L);
				
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(404);
	}
	
	@Test
	public void testaCadastro() {
		Teste t = new Teste();
		t.setId(1L);
		t.setNumero(1);
		t.setResultado("Impressionante");
		
		
		BDDMockito.when(tr.save(t)).thenReturn(t);
		
		ResponseEntity<Teste> result = restTemplate.postForEntity("/testes/cadastrar", t, Teste.class);
	
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(201);
	}
	
	@Test
	public void testaAtualizacao() {
		Teste t = new Teste();
		t.setId(1L);
		t.setNumero(1);
		t.setResultado("Perfeito");
		
		Optional<Teste> to = Optional.of(t);
		
		BDDMockito.when(tr.findById(t.getId())).thenReturn(to);
	
		Assertions.assertThat(to).isPresent();
		
		t.setId(2L);
		t.setNumero(2);
		t.setResultado("Muito bom");
		
		tr.save(t);
		
		BDDMockito.when(tr.findById(t.getId())).thenReturn(to);
		
		Assertions.assertThat(to).isPresent();
		
	}
	
	@Test
	public void testaAtualizacaoComFracasso() {
		
		Optional<Teste> to = tr.findById(200L);
		
		BDDMockito.when(tr.findById(200L)).thenReturn(to);
	
		Assertions.assertThat(to).isEmpty();
		
	}
	
	@Test
	public void testaRemocao() {
		Teste t = new Teste();
		t.setId(200L);
		t.setNumero(1);
		t.setResultado("Muito bom");
		
		//System.out.println("Remocao antes " + er.findById(e.getId()).get().getId());
		
		tr.save(t);
		
		BDDMockito.when(tr.findById(t.getId())).thenReturn(Optional.of(t));
		
		Assertions.assertThat(tr.findById(t.getId())).isPresent();
		
		tr.deleteById(t.getId());
		
		BDDMockito.when(tr.findById(t.getId())).thenReturn(Optional.empty());
		
		Assertions.assertThat(tr.findById(t.getId())).isEmpty();
		
	}
	
	

}
