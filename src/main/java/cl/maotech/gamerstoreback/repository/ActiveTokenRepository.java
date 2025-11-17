package cl.maotech.gamerstoreback.repository;

import cl.maotech.gamerstoreback.model.ActiveToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ActiveTokenRepository extends JpaRepository<ActiveToken, Long> {

    Optional<ActiveToken> findByUserId(Long userId);

    Optional<ActiveToken> findByToken(String token);

    @Modifying
    void deleteByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM ActiveToken a WHERE a.expiresAt < :now")
    void deleteExpiredTokens(LocalDateTime now);
}
