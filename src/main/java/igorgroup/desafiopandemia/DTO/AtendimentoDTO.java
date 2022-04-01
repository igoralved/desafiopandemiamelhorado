package igorgroup.desafiopandemia.DTO;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import igorgroup.desafiopandemia.model.Atendimento;

public class AtendimentoDTO {

	private Long id_a;
		private String descricao;
		private Boolean relacionadoComPandemia = false;
		private Boolean semPossibilidadeContagio = false;
		private LocalDate data = LocalDate.now();
		private Integer tempoAtendimento;
		private List<EtapaDTO> etapas = new ArrayList<>();
		private List<TesteDTO> testes = new ArrayList<>();
	
	public AtendimentoDTO(Atendimento a) {
		this.id_a = a.getId();
		this.descricao = a.getDescricao();
		this.relacionadoComPandemia = a.getRelacionado_com_pandemia();
		this.semPossibilidadeContagio = a.getSem_possibilidade_contagio();
		this.data = a.getData();
		this.tempoAtendimento = a.getTempoAtendimento();
		this.etapas.addAll(a.getEtapas().stream().map(EtapaDTO::new).collect(Collectors.toList()));
		this.testes.addAll(a.getTestes().stream().map(TesteDTO::new).collect(Collectors.toList()));
	}
	
	public Long getId() {
		return id_a;
	}
	
	public Boolean getRelacionado_com_pandemia(){
		return this.relacionadoComPandemia;
	}
	
	public Boolean getSem_possibilidade_contagio() {
		return this.semPossibilidadeContagio;
	}
	
	public List<EtapaDTO> getEtapas(){
		return this.etapas;
		}
	
	public List<TesteDTO> getTestes(){
		return this.testes;
		}
	
	public String getDescricao() {
		return descricao;
	}
	
	public boolean eRealcionadoComPandemia() {
		return relacionadoComPandemia;
	}
	
	public boolean descartouPossibilidadeContagio() {
		return semPossibilidadeContagio;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public Integer getTempoAtendimento() {
		return tempoAtendimento;
	}
	
	public String toString() {
		String result = "Descrição: " + descricao + ";";
		if(relacionadoComPandemia) {
			result += "Está relacionado com pandemia;";
		}else {
			result += "Não está relacionado com pandemia;";
		}
		if(semPossibilidadeContagio) {
			result += "Descartou possibilidade de contágio;";
		}else {
			result += "Não descartou possibilidade de contágio;";
		}result += "Tempo: " + tempoAtendimento;
		return result;
	}
	
	public void print() {
		System.out.println(toString());
	}
	
	public static Stream<AtendimentoDTO> converter(Stream<Atendimento> lista){
		return lista.map(AtendimentoDTO::new);
	}
	
	public static Stream<AtendimentoDTO> converter(List<Atendimento> lista){
		return lista.stream().map(AtendimentoDTO::new);
	}
	
	public static List<AtendimentoDTO> converterLista(List<Atendimento> lista){
		return lista.stream().map(AtendimentoDTO::new).collect(Collectors.toList());
	}
	
}
