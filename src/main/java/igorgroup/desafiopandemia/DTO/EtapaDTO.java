package  igorgroup.desafiopandemia.DTO;

import java.util.List;
import java.util.stream.Collectors;

import igorgroup.desafiopandemia.model.Etapa;

public class EtapaDTO {

	private Long id_e;
	
	private Integer numero;
	private String descricao;
	
	public EtapaDTO(Etapa e) {
		this.id_e = e.getId();
		this.numero = e.getNumero();
		this.descricao = e.getDescricao();
	}
	
	public Long getId() {
		return id_e;
	}
	
	public Integer getNumero() {
		return numero;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static List<EtapaDTO> converter(List<Etapa> lista){
		return lista.stream().map(EtapaDTO::new).collect(Collectors.toList());
	}
	
	public String toString() {
		return "Número: " + numero + "; " +
	"Descrição: " + descricao + ";";
	}
	
	public void print() {
		System.out.println(toString());
	}

	public static List<EtapaDTO> converterLista(List<Etapa> etapas) {
		// TODO Auto-generated method stub
		return etapas.stream().map(EtapaDTO::new).collect(Collectors.toList());
	}
	
}
