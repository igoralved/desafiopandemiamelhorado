package igorgroup.desafiopandemia.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import igorgroup.desafiopandemia.model.Usuario;
import io.swagger.models.auth.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfigurations {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("igorgroup.desafiopandemia"))
				.paths(PathSelectors.ant("/**"))
				.build().ignoredParameterTypes(Usuario.class)
				//.useDefaultResponseMessages(false)
				.globalOperationParameters(Arrays.asList(
						new ParameterBuilder()
						.name("Authorization")
						.description("Header para token JWT")
						.modelRef(new ModelRef("string"))
						.parameterType("header")
						.required(false)
						.build()
						))
				.globalResponseMessage(RequestMethod.GET, responseMessageForGet())
				.globalResponseMessage(RequestMethod.POST, responseMessageForPost())
				.globalResponseMessage(RequestMethod.PUT, responseMessageForPut())
				.globalResponseMessage(RequestMethod.DELETE, responseMessageForDelete())
				//.globalResponseMessage(RequestMethod.POST, responseMessageForAny())
				.securitySchemes(Arrays.asList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
		        .securityContexts(Arrays.asList(securityContext()))
		        .apiInfo(apiInfo());
	}
	
	private List<ResponseMessage> responseMessageForGet()
	{
	    return new ArrayList<ResponseMessage>() {
			private static final long serialVersionUID = 1L;

		{
	        add(new ResponseMessageBuilder()
	            .code(500)
	            .message("500 message")
	            .responseModel(new ModelRef("Error"))
	            .build());
	        add(new ResponseMessageBuilder()
	            .code(403)
	            .message("Forbidden!")
	            .build());
	    }};
	}
	
	private List<ResponseMessage> responseMessageForPost()
	{
	    return new ArrayList<ResponseMessage>() {
			private static final long serialVersionUID = 1L;

		{
	        add(new ResponseMessageBuilder()
	            .code(500)
	            .message("500 message")
	            .responseModel(new ModelRef("Error"))
	            .build());
	        add(new ResponseMessageBuilder()
		            .code(400)
		            .message("Bad request!")
		            .build());
	        add(new ResponseMessageBuilder()
	            .code(403)
	            .message("Forbidden!")
	            .build());
	    }};
	}
	
	private List<ResponseMessage> responseMessageForPut()
	{
	    return new ArrayList<ResponseMessage>() {
			private static final long serialVersionUID = 1L;

		{
	        add(new ResponseMessageBuilder()
	            .code(500)
	            .message("500 message")
	            .responseModel(new ModelRef("Error"))
	            .build());
	        add(new ResponseMessageBuilder()
		            .code(404)
		            .message("Not found!")
		            .build());
	        add(new ResponseMessageBuilder()
	            .code(403)
	            .message("Forbidden!")
	            .build());
	    }};
	}
	
	private List<ResponseMessage> responseMessageForDelete()
	{
	    return new ArrayList<ResponseMessage>() {
			private static final long serialVersionUID = 1L;

		{
	        add(new ResponseMessageBuilder()
	            .code(500)
	            .message("500 message")
	            .responseModel(new ModelRef("Error"))
	            .build());
	        add(new ResponseMessageBuilder()
		            .code(404)
		            .message("Not found!")
		            .build());
	        add(new ResponseMessageBuilder()
	            .code(403)
	            .message("Forbidden!")
	            .build());
	    }};
	}
	
	private SecurityContext securityContext() {
	    return SecurityContext.builder()
	        .securityReferences(defaultAuth())
	        //.forPaths(PathSelectors.any())
	        .forPaths(PathSelectors.ant("/atendimentos/**"))
	        .forPaths(PathSelectors.ant("/etapas/**"))
	        .forPaths(PathSelectors.ant("/testes/**"))
	        .forPaths(PathSelectors.ant("/unidades_de_saude/**"))
	        //.forPaths(PathSelectors.ant("/auth/**"))
	        .build();
	}
	
	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope
        	= new AuthorizationScope("ADMIN", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
			authorizationScopes[0] = authorizationScope;
		return Arrays.asList(
				new SecurityReference("Token Access", authorizationScopes));
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
	            .title("Desafio Pandemia")
	            .description("Verifique os atendimentos de unidades de saúde e seus testes, etapas e tempos mímimos, médio e máximos")
	            .build();
	}
	
	
}
