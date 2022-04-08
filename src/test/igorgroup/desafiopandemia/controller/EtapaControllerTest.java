package igorgroup.desafiopandemia.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import igorgroup.desafiopandemia.model.Etapa;
import igorgroup.desafiopandemia.repository.EtapaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EtapaControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@MockBean
	private EtapaRepository er;
	
	
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@TestConfiguration
	static class Config{
		@Bean
		public RestTemplateBuilder restTemplateBuilder(){
			return new RestTemplateBuilder().basicAuthentication("igor", "0000");
		}
	}
	

	
	
	@Test
	public void testaLista() {
		restTemplate = restTemplate.withBasicAuth("1", "1");
		ResponseEntity<String> result = restTemplate.getForEntity("/etapas/todas", String.class);
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void testaListaComMock() {
		
		//etapa 1
				Etapa e1 = new Etapa();
				e1.setId(1L);
				e1.setNumero(1);
				e1.setDescricao("Bem bom");
				
				//etapa 2
				
				Etapa e2 = new Etapa();
				e2.setId(2L);
				e2.setNumero(2);
				e2.setDescricao("Tudo certo");
		
		List<Etapa> lista = new ArrayList<>();
		lista.add(e1);
		lista.add(e2);
		
		BDDMockito.when(er.findAll()).thenReturn(lista);
		for(Etapa e: lista) {
			System.out.println(e.getId() + " " + e.getNumero() + " " + e.getDescricao());
		}
		ResponseEntity<String> result = restTemplate.getForEntity("/etapas/todas", String.class);
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void testaDetalheComMock() {
		//etapa 1
		Etapa e = new Etapa();
		e.setId(1L);
		e.setNumero(1);
		e.setDescricao("Bem bom");
		
		Optional<Etapa> eo = Optional.of(e);
		
		BDDMockito.when(er.findById(e.getId())).thenReturn(eo);
		System.out.println("ID " + e.getId());
		
		ResponseEntity<Etapa> result = restTemplate.getForEntity("/etapas/detalhar/{id}", Etapa.class, e.getId());
		
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void testaDetalheComMockFracasso() {
		//etapa 1
		Etapa e = new Etapa();
		e.setId(1L);
		e.setNumero(1);
		e.setDescricao("Bem bom");
			
		Optional<Etapa> eo = Optional.of(e);
				
		BDDMockito.when(er.findById(e.getId())).thenReturn(eo);
		System.out.println("ID " + e.getId());
				
		ResponseEntity<Etapa> result = restTemplate.getForEntity("/etapas/detalhar/{id}", Etapa.class, 2L);
				
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(404);
	}
	
	@Test
	public void testaCadastro() {
		Etapa e = new Etapa();
		e.setId(1L);
		e.setNumero(1);
		e.setDescricao("É para adultos");
		
		Optional<Etapa> eo = Optional.of(e);
		
		BDDMockito.when(er.save(e)).thenReturn(e);
		
		ResponseEntity<Etapa> result = restTemplate.postForEntity("/etapas/cadastrar", e, Etapa.class);
	
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(201);
	}
	
	@Test
	public void testaAtualizacao() {
		Etapa e = new Etapa();
		e.setId(1L);
		e.setNumero(1);
		e.setDescricao("Para acertos");
		
		Optional<Etapa> eo = Optional.of(e);
		
		BDDMockito.when(er.findById(e.getId())).thenReturn(eo);
	
		Assertions.assertThat(eo).isPresent();
		
	}
	
	@Test
	public void testaAtualizacaoComFracasso() {
		
		Optional<Etapa> eo = er.findById(200L);
		
		BDDMockito.when(er.findById(200L)).thenReturn(eo);
	
		Assertions.assertThat(eo).isEmpty();
		
	}
	
	@Test
	public void testaRemocao() {
		Etapa e = new Etapa();
		e.setId(200L);
		e.setNumero(1);
		e.setDescricao("Para acertos");
		
		//System.out.println("Remocao antes " + er.findById(e.getId()).get().getId());
		
		er.save(e);
		
		BDDMockito.when(er.findById(e.getId())).thenReturn(Optional.of(e));
		
		Assertions.assertThat(er.findById(e.getId())).isPresent();
		
		er.deleteById(e.getId());
		
		BDDMockito.when(er.findById(e.getId())).thenReturn(Optional.empty());
		
		Assertions.assertThat(er.findById(e.getId())).isEmpty();
		
	}
	
	
}
