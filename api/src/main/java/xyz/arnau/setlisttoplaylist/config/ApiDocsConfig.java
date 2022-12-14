package xyz.arnau.setlisttoplaylist.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Setlist to Playlist API", version = "v1"))
@SecurityScheme(
        name = "bearerAuth",
        type = HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "Music platform user access token"
)
public class ApiDocsConfig {
}
