package igorgroup.desafiopandemia.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
	
	@Test
	public void testaAtendimentos() {
		UnidadeSaude u = new UnidadeSaude();
		//atendimento 1
		Atendimento a1 = new Atendimento();
		a1.setDescricao("Ok");
		a1.setRelacionado_com_pandemia(false);
		a1.setSem_possibilidade_contagio(true);
		//atendimento 2
		Atendimento a2 = new Atendimento();
		a2.setDescricao("Excelente");
		a2.setRelacionado_com_pandemia(true);
		a2.setSem_possibilidade_contagio(false);
		
		List<Atendimento> atendimentos = new ArrayList<>();
		atendimentos.add(a1);
		atendimentos.add(a2);
		
		u.setAtendimentos(atendimentos);
		
		assertEquals(atendimentos, u.getAtendimentos());
		
	}

}
