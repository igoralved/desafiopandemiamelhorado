package igorgroup.desafiopandemia.DTO;

import static org.junit.Assert.*;

import org.junit.Test;

import igorgroup.desafiopandemia.model.Teste;

public class TesteDTOTest {

	@Test
	public void test() {
		Teste t = new Teste();
		Integer numero = 1;
		String resultado = "Aprovado";
		t.setNumero(numero);
		t.setResultado(resultado);
		
		TesteDTO tdto = new TesteDTO(t);
		assertEquals(t.getId(), tdto.getId());
		assertEquals(t.getNumero(), tdto.getNumero());
		assertEquals(t.getResultado(), tdto.getResultado());
	}

}
