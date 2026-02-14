package htw.gruppe.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Diese Klasse sagt Swagger, dass wir einen Schlüssel (Token) brauchen,
 *  * um geschützte Endpunkte zu benutzen.
 *
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        //Hier definieren wir den JWT Schlüssel
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                // Damit Swagger weiß, dass alle Endpunkte den Schlüssel brauchen
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
