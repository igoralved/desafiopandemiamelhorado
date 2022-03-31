package igorgroup.desafiopandemia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import igorgroup.desafiopandemia.DTO.TokenDTO;
import igorgroup.desafiopandemia.DTO.UsuarioDTO;
import igorgroup.desafiopandemia.form.LoginForm;
import igorgroup.desafiopandemia.model.Usuario;
import igorgroup.desafiopandemia.repository.UsuarioRepository;
import igorgroup.desafiopandemia.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
		
	@PostMapping
	@Transactional
	public ResponseEntity<TokenDTO> autenticar(@RequestParam(value="email", defaultValue = "") String email,
			@RequestParam(value="senha", defaultValue = "") String senha){
		UsernamePasswordAuthenticationToken dadoslogin = new UsernamePasswordAuthenticationToken(email,senha);
		
		try {
			Authentication a = authManager.authenticate(dadoslogin);
			String token = tokenService.gerarToken(a);
	
			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
		}catch(AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}

