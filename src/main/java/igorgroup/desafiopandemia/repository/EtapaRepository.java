package igorgroup.desafiopandemia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import igorgroup.desafiopandemia.model.Etapa;


public interface EtapaRepository extends JpaRepository<Etapa, Long> {

	@Query("SELECT e FROM Etapa e WHERE (:numero is null or e.numero = :numero)")
	List<igorgroup.desafiopandemia.model.Etapa> findByNumero(@Param("numero") String numero);
	
	@Query("SELECT e FROM Etapa e")
	List<Etapa> carregarTodas();
	
	//List<Etapa> findByIdE(Long id_e);
	
}
