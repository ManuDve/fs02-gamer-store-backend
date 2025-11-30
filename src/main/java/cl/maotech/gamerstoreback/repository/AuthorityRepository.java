package cl.maotech.gamerstoreback.repository;

import cl.maotech.gamerstoreback.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    List<Authority> findByUserId(Long userId);
    // Opción 1: Query Method con @Modifying
    @Modifying
    @Query("DELETE FROM Authority a WHERE a.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}

