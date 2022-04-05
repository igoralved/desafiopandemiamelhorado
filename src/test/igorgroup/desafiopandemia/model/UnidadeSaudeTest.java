package igorgroup.desafiopandemia.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class UnidadeSaudeTest {

	@Test
	public void test() {
		UnidadeSaude u = new UnidadeSaude();
		String nome = "CARDIOLOGIA";
		Integer numeroPacientes = 12;
		u.setNome(nome);
		u.setNumeroPacientes(numeroPacientes);
		
		assertEquals(nome,u.getNome());
		assertEquals(numeroPacientes,u.getNumeroPacientes());
	}

}
