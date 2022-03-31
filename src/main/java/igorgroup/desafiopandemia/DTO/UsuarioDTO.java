package igorgroup.desafiopandemia.DTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import igorgroup.desafiopandemia.model.Perfil;
import igorgroup.desafiopandemia.model.Usuario;

public class UsuarioDTO {

	private Long id;
	//private String nome;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority>  perfis = new ArrayList<>();
	
	
	public UsuarioDTO(Usuario u) {
		this.id = u.getId();
		this.email = u.getUsername();
		this.senha = u.getPassword();
		this.perfis = u.getAuthorities();
	}
	
	public Long getId() {
		return this.id;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.perfis;
	}

	public String getPassword() {
		return this.senha;
	}

	
	public String getUsername() {
		return this.email;
	}
	
	public static List<UsuarioDTO> converter(List<Usuario> lista){
		return lista.stream().map(UsuarioDTO::new).collect(Collectors.toList());
	}
	
}
