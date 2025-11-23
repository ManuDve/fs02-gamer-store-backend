package cl.maotech.gamerstoreback.config;

import cl.maotech.gamerstoreback.constant.SwaggerEndpoints;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(SwaggerEndpoints.OpenApiInfo.TITLE)
                        .version(SwaggerEndpoints.OpenApiInfo.VERSION)
                        .description(SwaggerEndpoints.OpenApiInfo.DESCRIPTION)
                        .contact(new Contact()
                                .name(SwaggerEndpoints.OpenApiInfo.CONTACT_NAME)
                                .email(SwaggerEndpoints.OpenApiInfo.CONTACT_EMAIL))
                        .license(new License()
                                .name(SwaggerEndpoints.OpenApiInfo.LICENSE_NAME)
                                .url(SwaggerEndpoints.OpenApiInfo.LICENSE_URL)))
                .servers(List.of(
                        new Server()
                                .url(SwaggerEndpoints.OpenApiInfo.SERVER_URL)
                                .description(SwaggerEndpoints.OpenApiInfo.SERVER_DESCRIPTION)))
                .addSecurityItem(new SecurityRequirement().addList(SwaggerEndpoints.OpenApiInfo.SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SwaggerEndpoints.OpenApiInfo.SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SwaggerEndpoints.OpenApiInfo.SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme(SwaggerEndpoints.OpenApiInfo.SECURITY_SCHEME)
                                        .bearerFormat(SwaggerEndpoints.OpenApiInfo.BEARER_FORMAT)
                                        .description(SwaggerEndpoints.OpenApiInfo.SECURITY_DESCRIPTION)));
    }
}
