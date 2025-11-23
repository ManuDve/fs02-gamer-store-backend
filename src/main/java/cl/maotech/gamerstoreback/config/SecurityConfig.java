package cl.maotech.gamerstoreback.config;

import cl.maotech.gamerstoreback.constant.AuthorityEndpoints;
import cl.maotech.gamerstoreback.constant.BlogPostEndpoints;
import cl.maotech.gamerstoreback.constant.Messages;
import cl.maotech.gamerstoreback.constant.OrderEndpoints;
import cl.maotech.gamerstoreback.constant.ProductEndpoints;
import cl.maotech.gamerstoreback.constant.SecurityRoles;
import cl.maotech.gamerstoreback.constant.SwaggerEndpoints;
import cl.maotech.gamerstoreback.constant.UserEndpoints;
import cl.maotech.gamerstoreback.dto.MessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    // Permitir acceso público al login y registro
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers(HttpMethod.POST, UserEndpoints.BASE).permitAll()

                    // Permitir acceso público a Swagger y OpenAPI
                    .requestMatchers(SwaggerEndpoints.SWAGGER_UI, SwaggerEndpoints.SWAGGER_UI_HTML).permitAll()
                    .requestMatchers(SwaggerEndpoints.API_DOCS, SwaggerEndpoints.API_DOCS_YAML).permitAll()

                    // Endpoints de Products - Acceso público para GET, solo ADMIN para modificaciones
                    .requestMatchers(HttpMethod.GET, ProductEndpoints.BASE_WITH_WILDCARD).permitAll()
                    .requestMatchers(HttpMethod.POST, ProductEndpoints.BASE).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.PUT, ProductEndpoints.FULL_ID).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.DELETE, ProductEndpoints.FULL_ID).hasRole(SecurityRoles.ADMIN)

                    // Endpoints de Blog Posts - Acceso público para GET, solo ADMIN para modificaciones
                    .requestMatchers(HttpMethod.GET, BlogPostEndpoints.BASE_WITH_WILDCARD).permitAll()
                    .requestMatchers(HttpMethod.POST, BlogPostEndpoints.BASE).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.PUT, BlogPostEndpoints.FULL_ID).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.DELETE, BlogPostEndpoints.FULL_ID).hasRole(SecurityRoles.ADMIN)

                    // Endpoints de Users
                    .requestMatchers(HttpMethod.GET, UserEndpoints.BASE).hasAnyRole(SecurityRoles.USER, SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.GET, UserEndpoints.FULL_ID).hasAnyRole(SecurityRoles.USER, SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.PUT, UserEndpoints.FULL_ID).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.DELETE, UserEndpoints.FULL_ID).hasRole(SecurityRoles.ADMIN)

                    // Endpoints de Authorities
                    .requestMatchers(HttpMethod.GET, AuthorityEndpoints.BASE).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.GET, AuthorityEndpoints.FULL_BY_USER).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.GET, AuthorityEndpoints.FULL_ID).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.POST, AuthorityEndpoints.BASE).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.PUT, AuthorityEndpoints.FULL_ID).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.DELETE, AuthorityEndpoints.FULL_ID).hasRole(SecurityRoles.ADMIN)

                    // Endpoints de Orders
                    .requestMatchers(HttpMethod.POST, OrderEndpoints.BASE).hasAnyRole(SecurityRoles.USER, SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.GET, OrderEndpoints.BASE).hasAnyRole(SecurityRoles.USER, SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.GET, OrderEndpoints.FULL_ALL).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.GET, OrderEndpoints.BASE_WITH_WILDCARD).hasAnyRole(SecurityRoles.USER, SecurityRoles.ADMIN)

                    // Cualquier otra petición debe estar autenticada
                    .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> exception
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        sendErrorResponse(response, Messages.Error.ACCESS_DENIED, HttpStatus.FORBIDDEN.value());
                    })
                    .authenticationEntryPoint((request, response, authException) -> {
                        sendErrorResponse(response, Messages.Error.AUTHENTICATION_FAILED, HttpStatus.UNAUTHORIZED.value());
                    })
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private void sendErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        MessageResponse messageResponse = new MessageResponse(message, status);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(messageResponse));
    }
}
