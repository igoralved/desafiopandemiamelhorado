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
	
	
	

}
