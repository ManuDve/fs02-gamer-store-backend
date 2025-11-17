package cl.maotech.gamerstoreback.repository;

import cl.maotech.gamerstoreback.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, String> {

    List<BlogPost> findByAutor(String autor);

    @Query("SELECT DISTINCT bp FROM BlogPost bp JOIN bp.blogPostTags bpt WHERE bpt.id.tag = :tag")
    List<BlogPost> findByTag(@Param("tag") String tag);
}