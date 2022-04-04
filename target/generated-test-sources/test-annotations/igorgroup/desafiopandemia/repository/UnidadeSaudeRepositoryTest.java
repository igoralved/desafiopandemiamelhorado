package igorgroup.desafiopandemia.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

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
		this.repository.save(u1);
		
		//asserções
		assertThat(u1.getId()).isNotNull();
		assertThat(u1.getNome()).isEqualTo("Cardiologia");
		assertThat(u1.getNumeroPacientes()).isEqualTo(20);
	}
	
	
	@Test
	public void testaUpdate() {
		UnidadeSaude u1 = new UnidadeSaude();
		u1.setNome("Moinhos");
		u1.setNumeroPacientes(30);
		
		UnidadeSaude u2 = new UnidadeSaude();
		u2.setNome("Clínicas");
		u2.setNumeroPacientes(25);
		
		this.repository.save(u1);
		u1.setNome(u2.getNome());
		u1.setNumeroPacientes(u2.getNumeroPacientes());
		
		this.repository.save(u1);
		u1 = this.repository.findById(u1.getId()).get();
		
		//asserções
		assertThat(u1.getNome()).isEqualTo(u2.getNome());
		assertThat(u1.getNumeroPacientes()).isEqualTo(u2.getNumeroPacientes());
		
	}
	
	@Test
	public void testaDelete() {
		UnidadeSaude u1 = new UnidadeSaude();
		u1.setNome("Moinhos");
		u1.setNumeroPacientes(30);
		this.repository.save(u1);
		repository.delete(u1);
		assertThat(this.repository.findById(u1.getId())).isEmpty();
	}

}
