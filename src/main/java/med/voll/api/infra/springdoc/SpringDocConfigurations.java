package med.voll.api.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//https://github.com/alura-es-cursos
@Configuration
public class SpringDocConfigurations {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info().title("Api Voll.med")
                        .description("API Rest de la apliacion de la linica voll med")
                        .contact(new Contact().name("Equipo Backend")
                                .email("backend@voll.med"))
                        .license((new License().name("Apache 2.02")
                                .url("http://voll.med/api/licencia"))));
    }
    @Bean
    public void massege(){
        System.out.println("bearer is working");
    }
}
