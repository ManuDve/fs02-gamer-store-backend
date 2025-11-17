package cl.maotech.gamerstoreback.service;

import cl.maotech.gamerstoreback.config.JwtConfig;
import cl.maotech.gamerstoreback.model.ActiveToken;
import cl.maotech.gamerstoreback.repository.ActiveTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final ActiveTokenRepository activeTokenRepository;
    private final JwtConfig jwtConfig;

    @Transactional
    public void saveToken(Long userId, String token) {
        // Eliminar token anterior del usuario si existe
        activeTokenRepository.deleteByUserId(userId);

        // Forzar flush para que el delete se ejecute antes del insert
        activeTokenRepository.flush();

        // Guardar nuevo token
        ActiveToken activeToken = ActiveToken.builder()
                .userId(userId)
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusSeconds(jwtConfig.getExpiration() / 1000))
                .build();

        activeTokenRepository.save(activeToken);
    }

    public boolean isTokenActive(String token) {
        return activeTokenRepository.findByToken(token)
                .map(activeToken -> activeToken.getExpiresAt().isAfter(LocalDateTime.now()))
                .orElse(false);
    }

    @Transactional
    public void revokeToken(Long userId) {
        activeTokenRepository.deleteByUserId(userId);
    }

    // Limpia tokens expirados cada hora
    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void cleanExpiredTokens() {
        activeTokenRepository.deleteExpiredTokens(LocalDateTime.now());
    }
}
