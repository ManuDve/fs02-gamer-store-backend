package cl.maotech.gamerstoreback.repository;

import cl.maotech.gamerstoreback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRespository extends JpaRepository <User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
