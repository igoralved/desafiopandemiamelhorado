package igorgroup.desafiopandemia.form;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import igorgroup.desafiopandemia.model.Atendimento;
import igorgroup.desafiopandemia.model.Etapa;
import igorgroup.desafiopandemia.model.Teste;
import igorgroup.desafiopandemia.repository.AtendimentoRepository;

public class AtendimentoForm {

	
	@javax.validation.constraints.NotNull
	private String descricao;
	private Boolean relacionadoComPandemia;
	private Boolean semPossibilidadeContagio;
	private LocalDate data = LocalDate.now();
	private Integer tempoAtendimento;
	
	private List<Etapa> etapas = new ArrayList<>();
	
	private List<Teste> testes = new ArrayList<>();
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public void setRelacionado_com_pandemia( boolean relacionadoComPandemia) {
		this.relacionadoComPandemia = relacionadoComPandemia; 
	}
	
	public void setSem_possibilidade_contagio(boolean spc) {
		this.semPossibilidadeContagio = spc;
	}
	
	public boolean getSem_possibilidade_contagio() {
		return this.semPossibilidadeContagio;
	}
	
	public boolean getRelacionado_com_pandemia(){
		return this.relacionadoComPandemia;
	}
	
	public LocalDate getData() {
		return this.data;
	}
	
	public Integer getTempoAtendimento() {
		return tempoAtendimento;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	public void setData(int anos, int meses, int dias) {
		data = LocalDate.of(anos, meses, dias);
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public void setTempoAtendimento(Integer tempo) {
		this.tempoAtendimento = tempo;
	}
	
	public void addEtapa(Etapa e) {
		etapas.add(e);
	}
	
	public void addTeste(Teste t) {
		if(testes.size() < 2) {
			testes.add(t);
		}
	}
	
	public Atendimento converter() {
		Atendimento a = new Atendimento();
		a.setDescricao(descricao);
		a.setSem_possibilidade_contagio(semPossibilidadeContagio);
		a.setRelacionado_com_pandemia(relacionadoComPandemia);
		a.setTempoAtendimento(tempoAtendimento);
		a.setEtapas(etapas);
		a.setTestes(testes);
		return a;
	}
	
	
	
	public Atendimento atualizar(Long id, AtendimentoRepository asr) {
		//List<Atendimento> a = asr.findAll();
		//for(Atendimento aaux : a) {
			//if(aaux.getId().equals(id)) {
				Atendimento aaux = asr.findById(id).get();
				aaux.setSem_possibilidade_contagio(this.semPossibilidadeContagio);
				aaux.setRelacionado_com_pandemia(this.relacionadoComPandemia);
				aaux.setDescricao(this.descricao);
				aaux.setData(this.data);
				aaux.setEtapas(this.etapas);
				aaux.setTempoAtendimento(this.tempoAtendimento);
				aaux.setTestes(this.testes);
				//asr.save(aaux);
				return aaux;
				
			//}
	}
	
}

