package igorgroup.desafiopandemia.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "ETAPA")
public class Etapa {

	@ApiModelProperty(value = "ID da etapa")
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter private Long id;
	
	//@Column(name = "numero")
	@ApiModelProperty(value = "Número da etapa")
	@Getter @Setter private Integer numero;
	
	//@Column(name = "descricao")
	@ApiModelProperty(value = "Descrição da etapa")
	@Getter @Setter private String descricao;

	/*
	
	public Etapa() {
		
	}
	
	public Etapa(int numero, String descricao) {
		this.numero = numero;
		this.descricao = descricao;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}if(obj == null) {
			return false;
		}if(getClass() != obj.getClass()) {
			return false;
		}Etapa outra = (Etapa) obj;
		if(id == null) {
			if(outra.getId() != null) {
				return false;
			}
		}else if(!getId().equals(outra.getId())) {
			return false;
		}return true;
	}
	
	public Long getId() {
		return id;
	}
	
	public Integer getNumero() {
		return numero;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setNumero(Integer numero){
		this.numero = numero;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String toString() {
		return "Número: " + numero + ";/n" +
	"Descrição: " + descricao + ";";
	}
	
	public void print() {
		System.out.println(toString());
	}
	*/
}
