package br.com.escola.escolacursoapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

// Camada: CONFIGURACAO.
// Configura o Swagger para enviar credenciais Basic Auth automaticamente.
@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Escola Curso API", version = "1.0",
                 description = "API de cadastro de Alunos, Professores e Cursos"),
    security = @SecurityRequirement(name = "basicAuth")
)
@SecurityScheme(
    name = "basicAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "basic"
)
public class OpenApiConfig {
}
