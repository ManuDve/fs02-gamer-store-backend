package cl.maotech.gamerstoreback.controller;

import cl.maotech.gamerstoreback.constant.AuthorityEndpoints;
import cl.maotech.gamerstoreback.constant.Messages;
import cl.maotech.gamerstoreback.constant.SwaggerMessages;
import cl.maotech.gamerstoreback.dto.AuthorityRequestDto;
import cl.maotech.gamerstoreback.dto.AuthorityResponseDto;
import cl.maotech.gamerstoreback.service.AuthorityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(AuthorityEndpoints.BASE)
@RequiredArgsConstructor
@Tag(name = SwaggerMessages.Authority.TAG_NAME, description = SwaggerMessages.Authority.TAG_DESCRIPTION)
public class AuthorityController {

    private final AuthorityService authorityService;

    @Operation(
            summary = SwaggerMessages.Authority.GET_ALL_SUMMARY,
            description = SwaggerMessages.Authority.GET_ALL_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403)
    })
    @GetMapping
    public ResponseEntity<List<AuthorityResponseDto>> getAllAuthorities() {
        List<AuthorityResponseDto> authorities = authorityService.getAllAuthorities();
        return ResponseEntity.ok(authorities);
    }

    @Operation(
            summary = SwaggerMessages.Authority.GET_BY_ID_SUMMARY,
            description = SwaggerMessages.Authority.GET_BY_ID_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403),
            @ApiResponse(responseCode = "404", description = SwaggerMessages.Response.NOT_FOUND_404)
    })
    @GetMapping(AuthorityEndpoints.BY_USER)
    public ResponseEntity<List<AuthorityResponseDto>> getAuthoritiesByUserId(
            @Parameter(description = SwaggerMessages.Authority.ID_PARAM_DESCRIPTION, example = SwaggerMessages.Authority.ID_PARAM_EXAMPLE) @PathVariable Long userId) {
        List<AuthorityResponseDto> authorities = authorityService.getAuthoritiesByUserId(userId);
        return ResponseEntity.ok(authorities);
    }

    @Operation(
            summary = SwaggerMessages.Authority.GET_BY_ID_SUMMARY,
            description = SwaggerMessages.Authority.GET_BY_ID_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403),
            @ApiResponse(responseCode = "404", description = SwaggerMessages.Response.NOT_FOUND_404)
    })
    @GetMapping(AuthorityEndpoints.ID)
    public ResponseEntity<AuthorityResponseDto> getAuthorityById(
            @Parameter(description = SwaggerMessages.Authority.ID_PARAM_DESCRIPTION, example = SwaggerMessages.Authority.ID_PARAM_EXAMPLE) @PathVariable Long id) {
        AuthorityResponseDto authority = authorityService.getAuthorityById(id);
        return ResponseEntity.ok(authority);
    }

    @Operation(
            summary = SwaggerMessages.Authority.CREATE_SUMMARY,
            description = SwaggerMessages.Authority.CREATE_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = SwaggerMessages.Response.CREATED_201),
            @ApiResponse(responseCode = "400", description = SwaggerMessages.Response.BAD_REQUEST_400),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403)
    })
    @PostMapping
    public ResponseEntity<AuthorityResponseDto> createAuthority(@Valid @RequestBody AuthorityRequestDto requestDto) {
        AuthorityResponseDto authority = authorityService.createAuthority(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(authority);
    }

    @Operation(
            summary = SwaggerMessages.Authority.CREATE_SUMMARY,
            description = SwaggerMessages.Authority.CREATE_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "400", description = SwaggerMessages.Response.BAD_REQUEST_400),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403),
            @ApiResponse(responseCode = "404", description = SwaggerMessages.Response.NOT_FOUND_404)
    })
    @PutMapping(AuthorityEndpoints.ID)
    public ResponseEntity<AuthorityResponseDto> updateAuthority(
            @Parameter(description = SwaggerMessages.Authority.ID_PARAM_DESCRIPTION, example = SwaggerMessages.Authority.ID_PARAM_EXAMPLE) @PathVariable Long id,
            @Valid @RequestBody AuthorityRequestDto requestDto) {
        AuthorityResponseDto authority = authorityService.updateAuthority(id, requestDto);
        return ResponseEntity.ok(authority);
    }

    @Operation(
            summary = SwaggerMessages.Authority.DELETE_SUMMARY,
            description = SwaggerMessages.Authority.DELETE_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403),
            @ApiResponse(responseCode = "404", description = SwaggerMessages.Response.NOT_FOUND_404)
    })
    @DeleteMapping(AuthorityEndpoints.ID)
    public ResponseEntity<Map<String, String>> deleteAuthority(
            @Parameter(description = SwaggerMessages.Authority.ID_PARAM_DESCRIPTION, example = SwaggerMessages.Authority.ID_PARAM_EXAMPLE) @PathVariable Long id) {
        authorityService.deleteAuthority(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", Messages.Authority.DELETED);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = SwaggerMessages.Authority.UPDATE_SUMMARY,
            description = SwaggerMessages.Authority.UPDATE_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "400", description = SwaggerMessages.Response.BAD_REQUEST_400),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403),
            @ApiResponse(responseCode = "404", description = SwaggerMessages.Response.NOT_FOUND_404)
    })
    @PutMapping("/user/{userId}/role")
    public ResponseEntity<AuthorityResponseDto> updateUserRole(
            @Parameter(description = "ID del usuario", example = "1") @PathVariable Long userId,
            @Parameter(description = "Nuevo rol (ROLE_USER o ROLE_ADMIN)", example = "ROLE_ADMIN") @RequestParam String role) {

        if (!role.equals("ROLE_USER") && !role.equals("ROLE_ADMIN")) {
            throw new IllegalArgumentException("El rol debe ser ROLE_USER o ROLE_ADMIN");
        }

        AuthorityResponseDto authority = authorityService.updateUserRole(userId, role);
        return ResponseEntity.ok(authority);
    }
}
