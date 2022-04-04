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

import igorgroup.desafiopandemia.model.Atendimento;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AtendimentoRepositoryTest {

	@Autowired
	private AtendimentoRepository repository;
	
	@Rule
	public ExpectedException ee = ExpectedException.none();
	
	@Test
	public void testaSave() {
		Atendimento a1 = new Atendimento();
		a1.setDescricao("A mais famosa");
		a1.setRelacionado_com_pandemia(true);
		a1.setSem_possibilidade_contagio(false);
		a1.setTempoAtendimento(4);
		this.repository.save(a1);
		
		//asserções
		assertThat(a1.getId()).isNotNull();
		assertThat(a1.getDescricao()).isEqualTo("A mais famosa");
		assertThat(a1.getRelacionado_com_pandemia()).isEqualTo(true);
		assertThat(a1.getSem_possibilidade_contagio()).isEqualTo(false);
		assertThat(a1.getTempoAtendimento()).isEqualTo(4);
		
	}
	
	@Test
	public void testaUpdate() {
		Atendimento a1 = new Atendimento();
		a1.setDescricao("A melhor da cidade");
		a1.setRelacionado_com_pandemia(true);
		a1.setSem_possibilidade_contagio(true);
		a1.setTempoAtendimento(4);
		
		Atendimento a2 = new Atendimento();
		a2.setDescricao("A melhor do mundo");
		a2.setRelacionado_com_pandemia(false);
		a2.setSem_possibilidade_contagio(false);
		a2.setTempoAtendimento(8);
		
		this.repository.save(a1);
		a1.setDescricao(a2.getDescricao());
		a1.setRelacionado_com_pandemia(a2.getRelacionado_com_pandemia());
		a1.setSem_possibilidade_contagio(a2.getSem_possibilidade_contagio());
		a1.setTempoAtendimento(a2.getTempoAtendimento());
		this.repository.save(a1);
		
		a1 = this.repository.findById(a1.getId()).get();
		
		//asserções
		assertThat(a1.getDescricao()).isEqualTo(a2.getDescricao());
		assertThat(a1.getRelacionado_com_pandemia()).isEqualTo(a2.getRelacionado_com_pandemia());
		assertThat(a1.getSem_possibilidade_contagio()).isEqualTo(a2.getSem_possibilidade_contagio());
		assertThat(a1.getTempoAtendimento()).isEqualTo(a2.getTempoAtendimento());
	}
	
	@Test
	public void testaDelete() {
		Atendimento a1 = new Atendimento();
		a1.setDescricao("A mais recomendada");
		a1.setRelacionado_com_pandemia(false);
		a1.setSem_possibilidade_contagio(true);
		a1.setTempoAtendimento(4);
		this.repository.save(a1);
		repository.delete(a1);
		assertThat(this.repository.findById(a1.getId())).isEmpty();
	}

}
