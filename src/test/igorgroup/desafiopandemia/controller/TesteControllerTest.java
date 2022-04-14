package igorgroup.desafiopandemia.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	@Autowired
	private TesteRepository tr;
	
	@Autowired
	private MockMvc mockMvc;
	
	@TestConfiguration
	static class Config{
		@Bean
		public RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder().basicAuthentication("igor", "0000");
		}
	}
	
	@Test
	public void testaLista() {
		try {
			System.out.println();
			System.out.println();
			System.out.println("GET");
			System.out.println();
			System.out.println();
			
			this.mockMvc.perform(
					get("/testes/todos"))
				.andDo(print()).andExpect(status().isOk());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void buscarPorId() {
		System.out.println();
		System.out.println();
		System.out.println("GET{id}");
		System.out.println();
		System.out.println();
		
		
		Teste t1 = new Teste();
		t1.setId(1L);
		t1.setNumero(1);
		t1.setResultado("Aprovado");
		
		this.tr.save(t1);
		
		try {
			this.mockMvc.perform(get("/testes/detalhar/{id}", t1.getId()))
			.andDo(print()).andExpect(status().isOk());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void buscarPorIdInexistente() {
		System.out.println();
		System.out.println();
		System.out.println("GET{id} FAILURE");
		System.out.println();
		System.out.println();
		
		try {
			this.mockMvc.perform(get("/testes/detalhar/{id}",100L))
			.andDo(print()).andExpect(status().isNotFound());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void cadastraTeste() {
		System.out.println();
		System.out.println();
		System.out.println("GET{id} FAILURE");
		System.out.println();
		System.out.println();
		
		try {
			Teste t1 = new Teste();
			t1.setId(2L);
			t1.setNumero(2);
			t1.setResultado("Metade lá");
			
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(t1);
			
			MvcResult mvcResult = this.mockMvc.perform(post("/testes/cadastrar")
					.contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
					.andExpect(status().isCreated())
					.andReturn();
			
			this.mockMvc.perform(get("/testes/detalhar/{id}",t1.getId()))
			.andDo(print()).andExpect(status().isOk()).andReturn();
			
			assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void atualizar() {
		
		Teste t1 = new Teste();
		t1.setId(1L);
		t1.setNumero(1);
		t1.setResultado("Aprovado");
		
		this.tr.save(t1);
		
		Teste t2 = new Teste();
		t2.setId(2L);
		t2.setNumero(2);
		t2.setResultado("Metade lá");
		
		System.out.println();
		System.out.println();
		System.out.println("PUT");
		System.out.println();
		System.out.println();
		
		try {
			
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(t1);
			
			this.mockMvc.perform(put("/testes/atualizar/{id}",t1.getId())
					.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andDo(print()).andExpect(status().isOk());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void deletar() {
		System.out.println();
		System.out.println();
		System.out.println("DELETE");
		System.out.println();
		System.out.println();
		
		Teste t3 = new Teste();
		t3.setId(3L);
		t3.setNumero(3);
		t3.setResultado("Graduado");
		
		this.tr.save(t3);
		
		try {
			this.mockMvc.perform(delete("/testes/remover/{id}",t3.getId()))
			.andDo(print()).andExpect(status().isOk());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	
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
	*/
	

}
