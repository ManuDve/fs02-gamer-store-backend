package cl.maotech.gamerstoreback.controller;

import cl.maotech.gamerstoreback.constant.AuthorityEndpoints;
import cl.maotech.gamerstoreback.constant.Messages;
import cl.maotech.gamerstoreback.dto.AuthorityRequestDto;
import cl.maotech.gamerstoreback.dto.AuthorityResponseDto;
import cl.maotech.gamerstoreback.service.AuthorityService;
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
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping
    public ResponseEntity<List<AuthorityResponseDto>> getAllAuthorities() {
        List<AuthorityResponseDto> authorities = authorityService.getAllAuthorities();
        return ResponseEntity.ok(authorities);
    }

    @GetMapping(AuthorityEndpoints.BY_USER)
    public ResponseEntity<List<AuthorityResponseDto>> getAuthoritiesByUserId(@PathVariable Long userId) {
        List<AuthorityResponseDto> authorities = authorityService.getAuthoritiesByUserId(userId);
        return ResponseEntity.ok(authorities);
    }

    @GetMapping(AuthorityEndpoints.ID)
    public ResponseEntity<AuthorityResponseDto> getAuthorityById(@PathVariable Long id) {
        AuthorityResponseDto authority = authorityService.getAuthorityById(id);
        return ResponseEntity.ok(authority);
    }

    @PostMapping
    public ResponseEntity<AuthorityResponseDto> createAuthority(@Valid @RequestBody AuthorityRequestDto requestDto) {
        AuthorityResponseDto authority = authorityService.createAuthority(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(authority);
    }

    @PutMapping(AuthorityEndpoints.ID)
    public ResponseEntity<AuthorityResponseDto> updateAuthority(
            @PathVariable Long id,
            @Valid @RequestBody AuthorityRequestDto requestDto) {
        AuthorityResponseDto authority = authorityService.updateAuthority(id, requestDto);
        return ResponseEntity.ok(authority);
    }

    @DeleteMapping(AuthorityEndpoints.ID)
    public ResponseEntity<Map<String, String>> deleteAuthority(@PathVariable Long id) {
        authorityService.deleteAuthority(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", Messages.Authority.DELETED);
        return ResponseEntity.ok(response);
    }
}
