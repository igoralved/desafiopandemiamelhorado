package igorgroup.desafiopandemia.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class EtapaTest {

	@Test
	public void test() {
		Etapa e = new Etapa();
		String descricao = "Etapa para iniciantes";
		Integer numero = 1;
		e.setNumero(numero);
		e.setDescricao(descricao);
		assertEquals(e.getDescricao(),descricao);
		assertEquals(e.getNumero(),numero);
	}

}
