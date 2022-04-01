package igorgroup.desafiopandemia.DTO;

import static org.junit.Assert.*;

import org.junit.Test;

import igorgroup.desafiopandemia.model.Etapa;

public class EtapaDTOTest {

	@Test
	public void test() {
		Etapa e = new Etapa();
		String descricao = "Etapa para iniciantes";
		Integer numero = 1;
		e.setNumero(numero);
		e.setDescricao(descricao);
		
		EtapaDTO edto = new EtapaDTO(e);
		
		assertEquals(e.getId(),edto.getId());
		assertEquals(e.getDescricao(),edto.getDescricao());
		assertEquals(e.getNumero(),edto.getNumero());
		
	}

}
