package igorgroup.desafiopandemia.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import igorgroup.desafiopandemia.model.UnidadeSaude;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UnidadeSaudeRepositoryTest {

	@Autowired
	private UnidadeSaudeRepository repository;
	
	@Rule
	public ExpectedException ee = ExpectedException.none();
	
	
	@Test
	public void testaSave() {
		UnidadeSaude u1 = new UnidadeSaude();
		u1.setNome("Cardiologia");
		u1.setNumeroPacientes(20);
		this.repository.saveAndFlush(u1);
		
		//asserçõe
		assertThat(repository.findById(u1.getId()).get().getId()).isNotNull();
		assertThat(repository.findById(u1.getId()).get().getNome()).isEqualTo("Cardiologia");
		assertThat(repository.findById(u1.getId()).get().getNumeroPacientes()).isEqualTo(20);
	}
	
	
	@Test
	public void testaUpdate() {
		UnidadeSaude u1 = repository.findById(1L).get();
		u1.setNome("CLINICAS");
		u1.setNumeroPacientes(12);
		this.repository.saveAndFlush(u1);
		
		//asserções
		assertThat(repository.findById(u1.getId()).get().getNome()).isEqualTo(u1.getNome());
		assertThat(repository.findById(u1.getId()).get().getNumeroPacientes()).isEqualTo(u1.getNumeroPacientes());
		
	}
	
	@Test
	public void testaDelete() {
		ArrayList<UnidadeSaude> unidades = (ArrayList<UnidadeSaude>) repository.findAll();
		repository.deleteAll(unidades);
		assertThat(this.repository.findAll()).isEmpty();
	}

}
