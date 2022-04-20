package igorgroup.desafiopandemia.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import igorgroup.desafiopandemia.model.Teste;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TesteRepositoryTest {

	@Autowired
	private TesteRepository repository;
	
	@Rule
	public ExpectedException ee = ExpectedException.none();
	
	@Test
	public void testaSave() {
		//teste 1
		Teste teste1 = new Teste();
		teste1.setNumero(1);
		teste1.setResultado("Ok");
		this.repository.saveAndFlush(teste1);
		
		//teste 2
		Teste teste2 = new Teste();
		teste2.setNumero(2);
		teste2.setResultado("Bom");
		this.repository.saveAndFlush(teste2);
		
		//asserções
		//teste 1
		assertThat(repository.findById(teste1.getId()).get().getId()).isNotNull();
		assertThat(repository.findById(teste1.getId()).get().getNumero()).isEqualTo(1);
		assertThat(repository.findById(teste1.getId()).get().getResultado()).isEqualTo("Ok");
		//teste 2
		assertThat(repository.findById(teste2.getId()).get().getId()).isNotNull();
		assertThat(repository.findById(teste2.getId()).get().getNumero()).isEqualTo(2);
		assertThat(repository.findById(teste2.getId()).get().getResultado()).isEqualTo("Bom");
	}
	
	@Test
	public void testaUpdate() {
		//teste 1
		Teste teste1 = repository.findById(1L).get();
		teste1.setNumero(1);
		teste1.setResultado("Ok");
				
		//teste 2
		//Teste teste2 = new Teste();
		//teste2.setNumero(2);
		//teste2.setResultado("Ótimo");
	
		this.repository.saveAndFlush(teste1);
		
		assertThat(repository.findById(teste1.getId()).get().getNumero()).isEqualTo(1);
		assertThat(repository.findById(teste1.getId()).get().getResultado()).isEqualTo("Ok");
	}
	
	@Test
	public void testaDelete() {
		//teste 1
		ArrayList<Teste> testes = (ArrayList<Teste>) repository.findAll();
		repository.deleteInBatch(testes);
		assertThat(repository.findAll()).isEmpty();
	}

}
