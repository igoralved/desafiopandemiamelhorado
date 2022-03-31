package  igorgroup.desafiopandemia.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import igorgroup.desafiopandemia.model.UnidadeSaude;

public class UnidadeSaudeDTO {

	private Long id;
		private String nome;
		private Integer numeroPacientes;
		private LocalDate data;
		private List<AtendimentoDTO> atendimentos = new ArrayList<>();
	
	public UnidadeSaudeDTO(UnidadeSaude u) {
		this.id = u.getId();
			this.nome = u.getNome();
			this.numeroPacientes = u.getNumeroPacientes();
			this.data = u.getData();
			this.atendimentos.addAll(u.getAtendimentos().stream().map(AtendimentoDTO::new).collect(Collectors.toList()));
			}
	
	public Long getId() {
		return id;
	}
	
	
	public String getNome() {
		return nome;
	}
	
	public Integer getNumeroPacientes() {
		return numeroPacientes;
	}
	
	public List<AtendimentoDTO> getAtendimentos(){
		return atendimentos;
		}
	
	public void setAtendimentos(List<AtendimentoDTO> lista){
		this.atendimentos = lista;
	}
	
	public String getData() {
		return data.toString();
	}
	
	public static List<UnidadeSaudeDTO> converter(List<UnidadeSaude> lista){
		return lista.stream().map(UnidadeSaudeDTO::new).collect(Collectors.toList());
	}
	
	public String toString() {
		return "Nome: " + this.nome + "; "
				+ "Número de pacientes: " + this.numeroPacientes + "; "
				+ "data: " + this.getData();
	}
	
	public void print() {
		System.out.println(toString());
	}
	
}
