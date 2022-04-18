package igorgroup.desafiopandemia.controller;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.boot.web.servlet.server.Jsp;
import org.springframework.context.annotation.Bean;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Optional;
import java.util.TimeZone;

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
	
	@Autowired
	private AtendimentoRepository ar;
	
	@Autowired
	private MockMvc mockMvc;
	
	@TestConfiguration
	static class Config{
		@Bean
		public RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder().basicAuthentication("Igor","0000");
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
			
			this.mockMvc.perform(get("/atendimentos/listar"))
			.andDo(print()).andExpect(status().isOk());
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void buscarPorId() {
		System.out.println();
		System.out.println();
		System.out.println("GET/{id}");
		System.out.println();
		System.out.println();
		
		Atendimento a = new Atendimento();
		a.setId(1L);
		a.setDescricao("Atendendo agora");
		a.setRelacionado_com_pandemia(true);
		a.setSem_possibilidade_contagio(false);
		a.setTempoAtendimento(12);
		this.ar.save(a);
		
		try {
			
			this.mockMvc.perform(get("/atendimentos/detalhar/{id}", a.getId()))
			.andDo(print())
			.andExpect(status().isOk());
			
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
			
			this.mockMvc.perform(get("/atendimentos/detalhar/{id}", 100L))
			.andDo(print())
			.andExpect(status().isNotFound());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testaCadastro() {
		System.out.println();
		System.out.println();
		System.out.println("POST");
		System.out.println();
		System.out.println();
		
		try {
			
			Atendimento a = new Atendimento();
			a.setId(1L);
			a.setDescricao("Atendendo até amanhã");
			a.setRelacionado_com_pandemia(true);
			a.setSem_possibilidade_contagio(false);
			a.setTempoAtendimento(11);
			
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = "{\"descricao\":\"Atendendo agora\",\"data\":\"2022-04-18\",\"tempoAtendimento\":12,\"etapas\":[],\"testes\":[],\"relacionado_com_pandemia\":true,\"sem_possibilidade_contagio\":false,\"id\":1}";
			
			MvcResult mvcResult = this.mockMvc.perform(
					post("/atendimentos/cadastrar")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonString)
						)
					.andDo(print())
					.andExpect(status().isCreated())
					.andReturn();
			
			this.mockMvc.perform(get("/atendimentos/detalhar/{id}", a.getId()))
			.andDo(print())
			.andExpect(status().isOk());
			
			assertEquals("application/json;charset=UTF-8",mvcResult.getResponse().getContentType());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void atualizar() {
		
		
		System.out.println();
		System.out.println();
		System.out.println("PUT");
		System.out.println();
		System.out.println();
		
		try {
			
			Atendimento a1 = new Atendimento();
			a1.setId(1L);
			a1.setDescricao("Atendendo até depois de amanhã");
			a1.setRelacionado_com_pandemia(true);
			a1.setSem_possibilidade_contagio(true);
			a1.setTempoAtendimento(11);
			
			this.ar.save(a1);
			
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = "{\"descricao\":\"Atendendo até depois de amanhã\",\"data\":\"2021-04-18\",\"tempoAtendimento\":12,\"etapas\":[],\"testes\":[],\"relacionado_com_pandemia\":true,\"sem_possibilidade_contagio\":false,\"id\":1}";
			this.mockMvc.perform(put("/atendimentos/atualizar/{id}",a1.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonString)
			)
			.andDo(print()).andExpect(status().isOk());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void deleta() {

		System.out.println();
		System.out.println();
		System.out.println("DELETE");
		System.out.println();
		System.out.println();
		
		
		try {
			
			Atendimento a1 = new Atendimento();
			a1.setId(2L);
			a1.setDescricao("Atendendo até depois de amanhã");
			a1.setRelacionado_com_pandemia(false);
			a1.setSem_possibilidade_contagio(false);
			a1.setTempoAtendimento(13);
			
			this.ar.save(a1);
			
			this.mockMvc.perform(delete("/atendimentos/remover/{id}",a1.getId()))
			.andDo(print()).andExpect(status().isOk());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
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
*/
}
