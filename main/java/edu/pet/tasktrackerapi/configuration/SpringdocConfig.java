package edu.pet.tasktrackerapi.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SpringdocConfig {
    //todo вынести в config-файл
    //http://localhost:8080/swagger-ui/index.html#/
    private final String DOC_TITLE = "Task-tracker-api documentation";
    private final String DOC_VERSION = "1.0.0";
    private final String DOC_DESCRIPTION = "";
    @Bean
    public OpenAPI baseOpenApi(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title(DOC_TITLE)
                                .version(DOC_VERSION)
                                .description(DOC_DESCRIPTION)
                );
    }
}
