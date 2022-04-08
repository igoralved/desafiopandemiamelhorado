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
	
	@MockBean
	private UnidadeSaudeRepository ur;
	
	
	@TestConfiguration
	static class Config{
		@Bean
		public RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder().basicAuthentication("igor", "0000");
		}
	}
	
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
	}

}
