package igorgroup.desafiopandemia.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TesteTest {

	@Test
	public void test() {
		Teste t = new Teste();
		Integer numero = 1;
		String resultado = "Aprovado";
		t.setNumero(numero);
		t.setResultado(resultado);
	}

}
