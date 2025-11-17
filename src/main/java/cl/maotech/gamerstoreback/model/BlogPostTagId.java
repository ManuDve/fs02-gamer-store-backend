package cl.maotech.gamerstoreback.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BlogPostTagId implements Serializable {

    @Column(name = "blog_post_id", length = 10)
    private String blogPostId;

    @Column(name = "tag", length = 100)
    private String tag;
}

