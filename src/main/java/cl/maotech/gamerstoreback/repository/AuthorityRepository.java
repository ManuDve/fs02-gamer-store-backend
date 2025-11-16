package cl.maotech.gamerstoreback.repository;

import cl.maotech.gamerstoreback.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    List<Authority> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}

