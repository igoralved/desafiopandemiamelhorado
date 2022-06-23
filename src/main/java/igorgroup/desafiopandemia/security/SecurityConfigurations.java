package igorgroup.desafiopandemia.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import igorgroup.desafiopandemia.repository.UsuarioRepository;
import igorgroup.desafiopandemia.service.AutenticacaoService;
import igorgroup.desafiopandemia.service.TokenService;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{

	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception{
		return super.authenticationManager();
	}
	
	//configurar a partir de autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(autenticacaoService).passwordEncoder(this.passwordEncoder);
	}
	
	
	//configurar a partir de autorização
	@Override
	protected void configure(HttpSecurity http) throws Exception{

		
		////
		
		http
		.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()).and()
		.authorizeRequests()
		//etapas
		.antMatchers(HttpMethod.GET, "/etapas/todas").permitAll()
		.antMatchers(HttpMethod.GET, "/etapas/detalhar/{id}").permitAll()
		.antMatchers(HttpMethod.POST, "/etapas/cadastrar").permitAll()
		.antMatchers(HttpMethod.PUT, "/etapas/atualizar/{id}").permitAll()
		.antMatchers(HttpMethod.DELETE, "/etapas/remover/{id}").permitAll()
		//testes
		.antMatchers(HttpMethod.GET, "/testes/todos").permitAll()
		.antMatchers(HttpMethod.GET, "/testes/detalhar/{id}").permitAll()
		.antMatchers(HttpMethod.POST, "/testes/cadastrar").permitAll()
		.antMatchers(HttpMethod.PUT, "/testes/atualizar/{id}").permitAll()
		.antMatchers(HttpMethod.DELETE, "/testes/remover/{id}").permitAll()
		//unidades de saúde
		.antMatchers(HttpMethod.GET, "/unidades").permitAll()
		.antMatchers(HttpMethod.GET, "/unidades/todas").permitAll()
		.antMatchers(HttpMethod.GET, "/unidades/atendendo").permitAll()
		.antMatchers(HttpMethod.GET, "/unidades/ordenado").permitAll()
		.antMatchers(HttpMethod.GET, "/unidades/paginado").permitAll()
		.antMatchers(HttpMethod.GET, "/unidades/num_pacientes_crescente").permitAll()
		.antMatchers(HttpMethod.GET, "/unidades/atendimentos_minimos").permitAll()
		.antMatchers(HttpMethod.GET, "/unidades/atendimentos_maximos").permitAll()
		.antMatchers(HttpMethod.GET, "/unidades/atendimentos_medios").permitAll()
		.antMatchers(HttpMethod.POST, "/unidades/cadastrar").permitAll()
		.antMatchers(HttpMethod.GET, "/unidades/detalhar/{id}").permitAll()
		.antMatchers(HttpMethod.PUT, "/unidades/atualizar/{id}").permitAll()
		.antMatchers(HttpMethod.DELETE, "/unidades/remover/{id}").permitAll()
		//atendimentos
		.antMatchers(HttpMethod.GET, "/atendimentos/listar").permitAll()
		.antMatchers(HttpMethod.POST, "/atendimentos/cadastrar").permitAll()
		.antMatchers(HttpMethod.GET, "/atendimentos/detalhar/{id}").permitAll()
		.antMatchers(HttpMethod.PUT, "/atendimentos/atualizar/{id}").permitAll()
		.antMatchers(HttpMethod.DELETE, "/atendimentos/remover/{id}").permitAll()
		.antMatchers(HttpMethod.GET, "/atendimentos/{id}/etapas").permitAll()
		.antMatchers(HttpMethod.GET, "/atendimentos/{id}/testes").permitAll()
		//outros
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		.antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
		.anyRequest().authenticated()
		.and().csrf().disable().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().addFilterBefore(new AutenticacaoViaTokenFilter(this.tokenService, this.usuarioRepository),UsernamePasswordAuthenticationFilter.class);
	}
	
	
	
	
	//configurar a partir de recursos estáticos (arquivos js, css, imagens, etc.)
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/**.html","/v2/api-docs","/webjars/**","/configuration/**","/swagger-resources/**");
	}
	
	@Bean
	  CorsConfigurationSource corsConfigurationSource() 
	  {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("*"));
	    configuration.setAllowedMethods(Arrays.asList("GET","POST"));
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	  }
	
}
