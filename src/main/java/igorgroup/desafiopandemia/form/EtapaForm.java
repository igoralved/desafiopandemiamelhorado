package igorgroup.desafiopandemia.form;

import java.util.List;

import igorgroup.desafiopandemia.model.Etapa;
import igorgroup.desafiopandemia.repository.EtapaRepository;

public class EtapaForm {

	private int numero;
	private String descricao;
	
	public int getNumero() {
		return this.numero;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public void setNumero(int numero){
		this.numero = numero;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Etapa converter() {
		Etapa e = new Etapa();
		e.setDescricao(this.descricao);
		e.setNumero(this.numero);
		return e;
	}
	
	
	
	public Etapa atualizar(Long id, EtapaRepository er) {
		//List<Etapa> lista = er.findAll();
		//for(Etapa e : lista) {
			//if(e.getId().equals(id)) {
		Etapa e = er.findById(id).get();
				e.setDescricao(this.descricao);
				e.setNumero(this.numero);
				return e;
			//}
		//}
		//return null;
	}
	
}
