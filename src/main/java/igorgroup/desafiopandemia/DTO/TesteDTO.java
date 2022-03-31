package  igorgroup.desafiopandemia.DTO;

import java.util.List;
import java.util.stream.Collectors;

import igorgroup.desafiopandemia.model.Teste;

public class TesteDTO {

	private Long id_t;
	
	private int numero;
	private String resultado;
	
	public TesteDTO(Teste t) {
		this.id_t = t.getId();
		this.numero = t.getNumero();
		this.resultado = t.getResultado();
	}
	
	public Long getId() {
		return id_t;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public String getResultado() {
		return resultado;
	}
	
	public String toString() {
		return "Número: " + numero + " Resultados: " + resultado;
	}
	
	public void print() {
		System.out.println(toString());
	}
	
	public static List<TesteDTO> converter(List<Teste> lista){
		return lista.stream().map(TesteDTO::new).collect(Collectors.toList());
	}
}

