package igorgroup.desafiopandemia.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "UNIDADE_SAUDE")
public class UnidadeSaude {

	@ApiModelProperty(value = "ID da unidade de saúde")
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter private Long id;
	
	@ApiModelProperty(value = "Nome da unidade de saúde")
	@Getter @Setter private String nome;
	
	@ApiModelProperty(value = "Número de pacientes da unidade de saúde")
	@Getter @Setter private Integer numeroPacientes;
	
	@Getter @Setter private LocalDate data = LocalDate.now();
	
	@ApiModelProperty(value = "Atendimentos da unidade de saúde")
	@OneToMany
	@JoinColumn(name = "id_unidade_saude")
	@NonNull @Getter @Setter private List<Atendimento> atendimentos = new ArrayList<Atendimento>();
	
	/*
	public UnidadeSaude() {
		
	}
		
	public UnidadeSaude(String nome, int numeroPacientes) {
		this.nome = nome;
		this.numeroPacientes = numeroPacientes;
		
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
		}UnidadeSaude outra = (UnidadeSaude) obj;
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
	
	public String getNome() {
		return nome;
	}
	
	public Integer getNumeroPacientes() {
		return numeroPacientes;
	}
	
	@OneToMany
	public List<Atendimento> getAtendimentos(){
		return atendimento;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setAtendimentos(List<Atendimento> lista){
		this.atendimento = lista;
	}
	
	public void setNumeroPacientes(Integer numeroPacientes) {
		this.numeroPacientes = numeroPacientes;
	}
	
	public String getData() {
		return data.toString();
	}
	
	public LocalDate getLocalDate() {
		return data;
	}
	
	public void setData(int ano, int meses, int dias) {
		this.data = LocalDate.of(ano, meses, dias);
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public void addAtendimento(Atendimento a) {
		atendimento.add(a);
	}
	
	public String toString() {
		return "Nome: " + nome + "; "
				+ "Número de pacientes: " + numeroPacientes + "; "
				+ "data: " + getData();
	}*/
	
	public void print() {
		System.out.println(toString());
	}
	
}
