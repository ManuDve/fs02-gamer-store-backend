package cl.maotech.gamerstoreback.controller;

import cl.maotech.gamerstoreback.constant.SwaggerMessages;
import cl.maotech.gamerstoreback.dto.LoginRequestDto;
import cl.maotech.gamerstoreback.dto.LoginResponseDto;
import cl.maotech.gamerstoreback.model.User;
import cl.maotech.gamerstoreback.service.CustomUserDetailsService;
import cl.maotech.gamerstoreback.service.TokenService;
import cl.maotech.gamerstoreback.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = SwaggerMessages.Auth.TAG_NAME, description = SwaggerMessages.Auth.TAG_DESCRIPTION)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Operation(
            summary = SwaggerMessages.Auth.LOGIN_SUMMARY,
            description = SwaggerMessages.Auth.LOGIN_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = SwaggerMessages.Auth.LOGIN_SUCCESS,
                    content = @Content(schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = SwaggerMessages.Auth.LOGIN_UNAUTHORIZED),
            @ApiResponse(
                    responseCode = "400",
                    description = SwaggerMessages.Auth.LOGIN_BAD_REQUEST)
    })
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
