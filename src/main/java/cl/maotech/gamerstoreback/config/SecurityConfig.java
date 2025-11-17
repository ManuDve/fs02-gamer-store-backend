package cl.maotech.gamerstoreback.config;

import cl.maotech.gamerstoreback.constant.AuthorityEndpoints;
import cl.maotech.gamerstoreback.constant.Messages;
import cl.maotech.gamerstoreback.constant.SecurityRoles;
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

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

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
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    // Permitir acceso público al login y registro
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers(HttpMethod.POST, UserEndpoints.BASE).permitAll()

                    // Endpoints de Users
                    .requestMatchers(HttpMethod.GET, UserEndpoints.BASE).hasAnyRole(SecurityRoles.USER, SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.GET, UserEndpoints.BASE + UserEndpoints.ID).hasAnyRole(SecurityRoles.USER, SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.PUT, UserEndpoints.BASE + UserEndpoints.ID).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.DELETE, UserEndpoints.BASE + UserEndpoints.ID).hasRole(SecurityRoles.ADMIN)

                    // Endpoints de Authorities
                    .requestMatchers(HttpMethod.GET, AuthorityEndpoints.BASE).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.GET, AuthorityEndpoints.BASE + AuthorityEndpoints.BY_USER).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.GET, AuthorityEndpoints.BASE + AuthorityEndpoints.ID).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.POST, AuthorityEndpoints.BASE).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.PUT, AuthorityEndpoints.BASE + AuthorityEndpoints.ID).hasRole(SecurityRoles.ADMIN)
                    .requestMatchers(HttpMethod.DELETE, AuthorityEndpoints.BASE + AuthorityEndpoints.ID).hasRole(SecurityRoles.ADMIN)

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
