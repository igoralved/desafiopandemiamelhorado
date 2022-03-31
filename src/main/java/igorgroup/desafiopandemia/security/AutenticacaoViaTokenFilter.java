package igorgroup.desafiopandemia.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import igorgroup.desafiopandemia.model.Usuario;
import igorgroup.desafiopandemia.repository.UsuarioRepository;
import igorgroup.desafiopandemia.service.TokenService;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public AutenticacaoViaTokenFilter(TokenService ts, UsuarioRepository ur) {
		this.tokenService = ts;
		this.usuarioRepository = ur;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recuperarToken(request);
		
		boolean valido = tokenService.isTokenValid(token);
		if(valido) {
			autenticarCliente(token);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7,token.length());
	}
	
	private void autenticarCliente(String token) {
		Long idUsuario = tokenService.getTokenId(token);
		Optional<Usuario> o =usuarioRepository.findById(idUsuario);
		if(o.isPresent()) {
			Usuario usuario = o.get();
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}

	
}
