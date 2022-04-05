package igorgroup.desafiopandemia.DTO;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import igorgroup.desafiopandemia.model.Atendimento;
import igorgroup.desafiopandemia.model.Etapa;
import igorgroup.desafiopandemia.model.Teste;

public class AtendimentoDTOTest {

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
		
		AtendimentoDTO adto = new AtendimentoDTO(a);
		
		assertEquals(a.getId(), adto.getId());
		assertEquals(a.getDescricao(), adto.getDescricao());
		assertEquals(a.getRelacionado_com_pandemia(), adto.getRelacionado_com_pandemia());
		assertEquals(a.getSem_possibilidade_contagio(), adto.getSem_possibilidade_contagio());
		assertEquals(a.getTempoAtendimento(), adto.getTempoAtendimento());
		
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
		
		AtendimentoDTO adto = new AtendimentoDTO(a);
		
		List<EtapaDTO> edtos = EtapaDTO.converter(a.getEtapas());
		
		for(int i = 0; i < adto.getEtapas().size(); i++) {
			assertEquals(adto.getEtapas().get(i).getId(), edtos.get(i).getId());
			assertEquals(adto.getEtapas().get(i).getNumero(), edtos.get(i).getNumero());
			assertEquals(adto.getEtapas().get(i).getDescricao(), edtos.get(i).getDescricao());
		}
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
		
		AtendimentoDTO adto = new AtendimentoDTO(a);
		
		List<TesteDTO> tdtos = TesteDTO.converter(a.getTestes());
		
		for(int i = 0; i < adto.getTestes().size(); i++) {
			assertEquals(adto.getTestes().get(i).getId(), tdtos.get(i).getId());
			assertEquals(adto.getTestes().get(i).getNumero(), tdtos.get(i).getNumero());
			assertEquals(adto.getTestes().get(i).getResultado(), tdtos.get(i).getResultado());
		}
		
	}

}
