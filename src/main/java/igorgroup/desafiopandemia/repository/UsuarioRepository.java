package igorgroup.desafiopandemia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import igorgroup.desafiopandemia.model.Usuario;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario,Long>{


	Optional<Usuario> findByEmail(String email);
	
}
