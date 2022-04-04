package igorgroup.desafiopandemia.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import igorgroup.desafiopandemia.DTO.EtapaDTO;
import igorgroup.desafiopandemia.model.Etapa;

@RunWith(SpringRunner.class)
@WebMvcTest(EtapaController.class)
public class EtapaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EtapaController c;
	
	@Test
	public void testaLista() {
	List<EtapaDTO> result = new ArrayList<>();
	Etapa e = new Etapa();
	e.setNumero(1);
	e.setDescricao("Legal");
	result.add(new EtapaDTO(e));
	Mockito.when(c.listar());
	}

}
