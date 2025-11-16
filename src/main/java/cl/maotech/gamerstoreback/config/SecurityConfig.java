package cl.maotech.gamerstoreback.config;

import cl.maotech.gamerstoreback.constant.AuthorityEndpoints;
import cl.maotech.gamerstoreback.constant.SecurityRoles;
import cl.maotech.gamerstoreback.constant.UserEndpoints;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                configurer
                        // Endpoints de Users
                        .requestMatchers(HttpMethod.GET, UserEndpoints.BASE).hasAnyRole(SecurityRoles.USER, SecurityRoles.ADMIN)
                        .requestMatchers(HttpMethod.GET, UserEndpoints.BASE + UserEndpoints.ID).hasAnyRole(SecurityRoles.USER, SecurityRoles.ADMIN)
                        .requestMatchers(HttpMethod.POST, UserEndpoints.BASE).hasRole(SecurityRoles.ADMIN)
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
        );

        // Usar autenticación HTTP Basic
        http.httpBasic(Customizer.withDefaults());

        // Deshabilitar CSRF (no requerido para APIs REST stateless que usan POST, PUT, DELETE, PATCH)
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
