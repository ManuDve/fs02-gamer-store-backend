package cl.maotech.gamerstoreback.controller;

import cl.maotech.gamerstoreback.dto.LoginRequestDto;
import cl.maotech.gamerstoreback.dto.LoginResponseDto;
import cl.maotech.gamerstoreback.model.User;
import cl.maotech.gamerstoreback.service.CustomUserDetailsService;
import cl.maotech.gamerstoreback.service.TokenService;
import cl.maotech.gamerstoreback.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        // Autenticar usuario
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Cargar detalles del usuario
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        // Generar token JWT
        final String jwt = jwtUtil.generateToken(userDetails);

        // Guardar token como activo (invalida el token anterior si existe)
        if (userDetails instanceof User) {
            tokenService.saveToken(((User) userDetails).getId(), jwt);
        }

        // Extraer roles del usuario
        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());

        // Construir respuesta
        LoginResponseDto response = LoginResponseDto.builder()
                .token(jwt)
                .type("Bearer")
                .email(userDetails.getUsername())
                .name(userDetails instanceof User ? ((User) userDetails).getName() : null)
                .roles(roles)
                .build();

        return ResponseEntity.ok(response);
    }
}
