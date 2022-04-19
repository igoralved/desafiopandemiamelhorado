package igorgroup.desafiopandemia.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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

import igorgroup.desafiopandemia.model.UnidadeSaude;
import igorgroup.desafiopandemia.repository.UnidadeSaudeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UnidadeSaudeControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private UnidadeSaudeRepository ur;
	
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
	public void listaTodas() {
		System.out.println();
		System.out.println();
		System.out.println("GET ALL");
		System.out.println();
		System.out.println();
		
		try {
			
			this.mockMvc.perform(get("/unidades/todas"))
			.andDo(print())
			.andExpect(status().isOk());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void filtrarAsQueAtendem() {
		
		System.out.println();
		System.out.println();
		System.out.println("GET (Apenas as que estão atendendo)");
		System.out.println();
		System.out.println();
		
		try {
			
			this.mockMvc.perform(get("/unidades/atendendo"))
			.andDo(print()).andExpect(status().isOk());
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void listarOrdenado() {
		
		System.out.println();
		System.out.println();
		System.out.println("GET ALL SORTED");
		System.out.println();
		System.out.println();
		
		try {
			
			this.mockMvc.perform(get("/unidades/ordenado"))
			.andDo(print()).andExpect(status().isOk());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void listarPaginado() {
		System.out.println();
		System.out.println();
		System.out.println("GET ALL WITH PAGES");
		System.out.println();
		System.out.println();
		
		try {
			
			this.mockMvc.perform(get("/unidades/paginado"))
			.andDo(print()).andExpect(status().isOk());
			
		}catch(Exception e) {
			
		}
		
	}
	
	@Test
	public void testaComNumeroPacientesCrescente() {
		
		System.out.println();
		System.out.println();
		System.out.println("GET ALL WITH CRESCENT NUMBER OF PATIENTS");
		System.out.println();
		System.out.println();
	
		try {
			this.mockMvc.perform(get("/unidades/num_pacientes_crescente?data=" + "2021-08-25"))
			.andDo(print()).andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testaComNumeroPacientesCrescenteVazio() {
		
		System.out.println();
		System.out.println();
		System.out.println("GET ALL WITH CRESCENT NUMBER OF PATIENTS (NO CONTENT)");
		System.out.println();
		System.out.println();
	
		try {
			this.mockMvc.perform(get("/unidades/num_pacientes_crescente?data=" + LocalDate.now()))
			.andDo(print()).andExpect(status().isNoContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testaComAtendimentosMinimos() {
		System.out.println();
		System.out.println();
		System.out.println("GET ATENDIMENTOS MINIMOS");
		System.out.println();
		System.out.println();
		try {
			this.mockMvc.perform(get("/unidades/atendimentos_minimos?data=" + "2021-08-25"))
			.andDo(print()).andExpect(status().isOk());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testaComAtendimentosMedios() {
		System.out.println();
		System.out.println();
		System.out.println("GET ATENDIMENTOS MEDIOS");
		System.out.println();
		System.out.println();
		try {
			this.mockMvc.perform(get("/unidades/atendimentos_medios?data=" + "2021-08-25"))
			.andDo(print()).andExpect(status().isOk());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testaComAtendimentosMaximos() {
		System.out.println();
		System.out.println();
		System.out.println("GET ATENDIMENTOS MAXIMOS");
		System.out.println();
		System.out.println();
		try {
			this.mockMvc.perform(get("/unidades/atendimentos_maximos?data=" + "2021-08-25"))
			.andDo(print()).andExpect(status().isOk());
		}catch(Exception e) {
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
		
		UnidadeSaude u = new UnidadeSaude();
		u.setId(1L);
		u.setNome("CUSTOM CLINIC");
		u.setNumeroPacientes(1);
		
		this.ur.save(u);
		
		try {
		
			this.mockMvc.perform(get("/unidades/detalhar/{id}", u.getId()))
			.andDo(print()).andExpect(status().isOk());
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testarPorIdInexistente() {
		
		System.out.println();
		System.out.println();
		System.out.println("GET{id} FAILURE");
		System.out.println();
		System.out.println();
		
		try {
			this.mockMvc.perform(get("/unidades/detalhar/{id}", 100L))
			.andDo(print()).andExpect(status().isNotFound());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void cadastrarUnidadeSaude() {
		System.out.println();
		System.out.println();
		System.out.println("POST");
		System.out.println();
		System.out.println();
		
		UnidadeSaude u = new UnidadeSaude();
		u.setId(2L);
		u.setNome("MUSHROOM MEDIC");
		u.setNumeroPacientes(1);
		
		
		try {
		
			String jsonString = "{\"id\":1,\"nome\":\"CUSTOM CLINIC\",\"numeroPacientes\":12,\"data\":\"2022-04-19\",\"atendimentos\":[]}";
			
			MvcResult result = this.mockMvc.perform(
					post("/unidades/cadastrar")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					).andDo(print())
					.andExpect(status().isCreated())
					.andReturn();
			
			assertEquals("application/json;charset=UTF-8",result.getResponse().getContentType());
			
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
		
		UnidadeSaude u1 = new UnidadeSaude();
		u1.setId(12L);
		u1.setNome("Trauma Center");
		u1.setNumeroPacientes(2);
		this.ur.save(u1);
		try {
			String jsonString = "{\"id\":1,\"nome\":\"CUSTOM CLINIC\",\"numeroPacientes\":12,\"data\":\"2022-04-19\",\"atendimentos\":[]}";
		
			this.mockMvc.perform(put("/unidades/atualizar/{id}",u1.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonString))
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
		
		try {
			UnidadeSaude u1 = new UnidadeSaude();
			u1.setId(13L);
			u1.setNome("CAXIAS");
			u1.setNumeroPacientes(16);
			
			this.ur.save(u1);
			this.mockMvc.perform(delete("/unidades/remover/{id}",u1.getId()))
			.andDo(print())
			.andExpect(status().isOk());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	@Test
	public void testaLista() {
		restTemplate = restTemplate.withBasicAuth("1", "1");
		ResponseEntity<String> result = restTemplate.getForEntity("/unidades/todas", String.class);
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void testaListaComMock() {
		UnidadeSaude u1 = new UnidadeSaude();
		u1.setId(1L);
		u1.setNome("CARDIOLOGIA");
		u1.setNumeroPacientes(11);
		
		UnidadeSaude u2 = new UnidadeSaude();
		u2.setId(2L);
		u2.setNome("MOINHOS");
		u2.setNumeroPacientes(22);
		
		List<UnidadeSaude> lista = new ArrayList<>();
		lista.add(u1);
		lista.add(u2);
		
		BDDMockito.when(ur.findAll()).thenReturn(lista);
		
		ResponseEntity<String> result = restTemplate.getForEntity("/unidades/todas", String.class);
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(200);
		
		
	}
	
	@Test
	public void testaCadastro() {
		UnidadeSaude u1 = new UnidadeSaude();
		u1.setId(13L);
		u1.setNome("CLINICAS");
		u1.setNumeroPacientes(21);
		
		BDDMockito.when(ur.save(u1)).thenReturn(u1);
		ResponseEntity<UnidadeSaude> result = restTemplate.postForEntity("/unidades/cadastrar", u1, UnidadeSaude.class);
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(201);
	}
	
	@Test
	public void testaDetalhar() {
		UnidadeSaude u1 = new UnidadeSaude();
		u1.setId(14L);
		u1.setNome("HORIZONTE");
		u1.setNumeroPacientes(10);
		
		BDDMockito.when(ur.findById(u1.getId())).thenReturn(Optional.of(u1));
		ResponseEntity<UnidadeSaude> result = restTemplate.getForEntity("/unidades/detalhar/{id}", UnidadeSaude.class, u1.getId());
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void testaDetalharComFracasso() {
		UnidadeSaude u1 = new UnidadeSaude();
		u1.setId(13L);
		u1.setNome("Literal");
		u1.setNumeroPacientes(30);
		
		BDDMockito.when(ur.findById(u1.getId())).thenReturn(Optional.of(u1));
		ResponseEntity<UnidadeSaude> result = restTemplate.getForEntity("/unidades/detalhar/{id}", UnidadeSaude.class, 31L);
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(404);
	}
	
	@Test
	public void testaAtualizacao() {
		UnidadeSaude u1 = new UnidadeSaude();
		u1.setId(3L);
		u1.setNome("LL");
		u1.setNumeroPacientes(37);
		
		Optional<UnidadeSaude> uo = Optional.of(u1);
		BDDMockito.when(ur.findById(u1.getId())).thenReturn(uo);
		
		Assertions.assertThat(uo).isPresent();
		
		u1.setId(4L);
		u1.setNome("XL");
		u1.setNumeroPacientes(74);
		
		ur.save(u1);
		
		BDDMockito.when(ur.findById(u1.getId())).thenReturn(uo);
		Assertions.assertThat(uo).isPresent();
	}
	
	@Test
	public void testaAtualizacaoComFracasso() {
		Optional<UnidadeSaude> uo = ur.findById(1L);
		BDDMockito.when(ur.findById(1L)).thenReturn(uo);
		Assertions.assertThat(uo).isEmpty();
	}
	
	@Test
	public void testaDeleta() {
		UnidadeSaude u1 = new UnidadeSaude();
		u1.setId(6L);
		u1.setNome("XX");
		u1.setNumeroPacientes(7);
		
		ur.save(u1);
		
		BDDMockito.when(ur.findById(u1.getId())).thenReturn(Optional.of(u1));
		Assertions.assertThat(ur.findById(u1.getId())).isPresent();
		
		ur.deleteById(u1.getId());
		
		BDDMockito.when(ur.findById(u1.getId())).thenReturn(Optional.empty());
		Assertions.assertThat(ur.findById(u1.getId())).isNotPresent();
	}*/

}
