package igorgroup.desafiopandemia.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Test;

public class AtendimentoTest {

	@Test
	public void test() {
		Atendimento a = new Atendimento();
		String descricao = "Primeiro atendimento";
		Boolean relacionadoComPandemia = true;
		Boolean semPossibilidadeContagio = true;
		Integer tempoAtendimento = 10;
		a.setDescricao(descricao);
		a.setRelacionado_com_pandemia(relacionadoComPandemia);
		a.setSem_possibilidade_contagio(semPossibilidadeContagio);
		a.setTempoAtendimento(tempoAtendimento);
		assertEquals(descricao, a.getDescricao());
		assertEquals(relacionadoComPandemia, a.getRelacionado_com_pandemia());
		assertEquals(semPossibilidadeContagio, a.getSem_possibilidade_contagio());
		assertEquals(tempoAtendimento, a.getTempoAtendimento());
	}
	
	@Test
	public void testaEtapas() {
		Atendimento a = new Atendimento();
		//etapa 1
		Etapa etapa1 = new Etapa();
		etapa1.setNumero(1);
		etapa1.setDescricao("Iniciante");
		//etapa 2
		Etapa etapa2 = new Etapa();
		etapa2.setNumero(2);
		etapa2.setDescricao("Intermediária");
		//etapa3
		Etapa etapa3 = new Etapa();
		etapa3.setNumero(3);
		etapa3.setDescricao("Final");
		
		
		List<Etapa> etapas = new ArrayList<>();
		etapas.add(etapa1);
		etapas.add(etapa2);
		etapas.add(etapa3);
		
		a.setEtapas(etapas);
		
		assertEquals(etapas, a.getEtapas());
	}
	
	@Test
	public void testaTestes() {
		Atendimento a = new Atendimento();
		//teste 1
		Teste teste1 = new Teste();
		teste1.setNumero(1);
		teste1.setResultado("Bom");
		//teste 2
		Teste teste2 = new Teste();
		teste2.setNumero(2);
		teste2.setResultado("Muito bom");
		
		List<Teste> testes = new ArrayList<>();
		testes.add(teste1);
		testes.add(teste2);
		
		a.setTestes(testes);
		
		assertEquals(testes, a.getTestes());
		
	}

}
