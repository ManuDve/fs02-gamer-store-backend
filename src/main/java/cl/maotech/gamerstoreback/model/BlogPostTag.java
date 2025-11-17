package cl.maotech.gamerstoreback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "blog_post_tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostTag {

    @EmbeddedId
    private BlogPostTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("blogPostId")
    @JoinColumn(name = "blog_post_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BlogPost blogPost;
}

