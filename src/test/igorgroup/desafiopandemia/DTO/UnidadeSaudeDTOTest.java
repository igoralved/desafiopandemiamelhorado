package igorgroup.desafiopandemia.DTO;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import igorgroup.desafiopandemia.model.Atendimento;
import igorgroup.desafiopandemia.model.UnidadeSaude;

public class UnidadeSaudeDTOTest {

	@Test
	public void test() {
		UnidadeSaude u = new UnidadeSaude();
		String nome = "CARDIOLOGIA";
		Integer numeroPacientes = 12;
		u.setNome(nome);
		u.setNumeroPacientes(numeroPacientes);
		
		UnidadeSaudeDTO udto = new UnidadeSaudeDTO(u);
		
		assertEquals(udto.getNome(),u.getNome());
		assertEquals(udto.getNumeroPacientes(),u.getNumeroPacientes());
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
		
		UnidadeSaudeDTO udto = new UnidadeSaudeDTO(u);
		
		List<AtendimentoDTO> adtos = AtendimentoDTO.converterLista(u.getAtendimentos());
		for(int i = 0; i < udto.getAtendimentos().size(); i++) {
			assertEquals(udto.getAtendimentos().get(i).getId(), adtos.get(i).getId());
			assertEquals(udto.getAtendimentos().get(i).getDescricao(), adtos.get(i).getDescricao());
			assertEquals(udto.getAtendimentos().get(i).getRelacionado_com_pandemia(), adtos.get(i).getRelacionado_com_pandemia());
			assertEquals(udto.getAtendimentos().get(i).getSem_possibilidade_contagio(), adtos.get(i).getSem_possibilidade_contagio());
		}
	}

}
