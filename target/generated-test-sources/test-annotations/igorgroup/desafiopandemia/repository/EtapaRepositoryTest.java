package igorgroup.desafiopandemia.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import igorgroup.desafiopandemia.model.Etapa;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EtapaRepositoryTest {

	@Autowired
	private EtapaRepository repository;
	
	@Rule
	public ExpectedException ee = ExpectedException.none();
	
	@Test
	public void testaSave() {
		//etapa 1
		Etapa etapa1 = new Etapa();
		etapa1.setNumero(1);
		etapa1.setDescricao("Iniciando");
		this.repository.save(etapa1);
		//etapa 2
		Etapa etapa2 = new Etapa();
		etapa2.setNumero(2);
		etapa2.setDescricao("No meio");
		this.repository.save(etapa2);
		//etapa 3
		Etapa etapa3 = new Etapa();
		etapa3.setNumero(3);
		etapa3.setDescricao("No fim");
		this.repository.save(etapa3);
		
		//asserções
		//etapa 1
		assertThat(etapa1.getId()).isNotNull();
		assertThat(etapa1.getNumero()).isEqualTo(1);
		assertThat(etapa1.getDescricao()).isEqualTo("Iniciando");
		//etapa 2
		assertThat(etapa2.getId()).isNotNull();
		assertThat(etapa2.getNumero()).isEqualTo(2);
		assertThat(etapa2.getDescricao()).isEqualTo("No meio");
		//etapa 3
		assertThat(etapa3.getId()).isNotNull();
		assertThat(etapa3.getNumero()).isEqualTo(3);
		assertThat(etapa3.getDescricao()).isEqualTo("No fim");
	}
	
	@Test
	public void testaUpdate() {
		//etapa 1
		Etapa etapa1 = new Etapa();
		etapa1.setNumero(1);
		etapa1.setDescricao("Iniciando");
		
		//etapa 2
		Etapa etapa2 = new Etapa();
		etapa2.setNumero(2);
		etapa2.setDescricao("No meio");
		
		this.repository.save(etapa1);
		
		etapa1.setNumero(etapa2.getNumero());
		etapa1.setDescricao(etapa2.getDescricao());
		this.repository.save(etapa1);
	
		etapa1 = this.repository.findById(etapa1.getId()).get();
		
		assertThat(etapa1.getNumero()).isEqualTo(2);
		assertThat(etapa1.getDescricao()).isEqualTo("No meio");
		
	}

	@Test
	public void testaDelete() {
		//etapa 1
		Etapa etapa1 = new Etapa();
		etapa1.setNumero(1);
		etapa1.setDescricao("Iniciando");
		
		this.repository.save(etapa1);
		repository.delete(etapa1);
		
		assertThat(repository.findById(etapa1.getId())).isEmpty();
	}
	
}
