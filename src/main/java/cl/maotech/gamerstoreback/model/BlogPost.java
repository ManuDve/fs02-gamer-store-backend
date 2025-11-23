package cl.maotech.gamerstoreback.model;

import cl.maotech.gamerstoreback.constant.Messages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blog_posts")
public class BlogPost {

    @Id
    @Column(length = 10)
    private String id;

    @NotBlank(message = "El título es requerido")
    @NotBlank(message = Messages.Validation.TITLE_REQUIRED)
    private String titulo;

    @NotBlank(message = "El autor es requerido")
    @NotBlank(message = Messages.Validation.AUTHOR_REQUIRED)
    private String autor;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(length = 1000)
    private String imagen;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String articulo;

    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BlogPostTag> blogPostTags = new ArrayList<>();

    // Métodos helper para trabajar con las etiquetas como antes
    @Transient
    public List<String> getEtiquetas() {
        return blogPostTags.stream()
            .map(bpt -> bpt.getId().getTag())
            .collect(Collectors.toList());
    }

    @Transient
    public void setEtiquetas(List<String> etiquetas) {
        this.blogPostTags.clear();
        if (etiquetas != null) {
            for (String tag : etiquetas) {
                BlogPostTagId tagId = new BlogPostTagId(this.id, tag);
                BlogPostTag blogPostTag = new BlogPostTag(tagId, this);
                this.blogPostTags.add(blogPostTag);
            }
        }
    }

    // Método para agregar una etiqueta
    public void addEtiqueta(String tag) {
        BlogPostTagId tagId = new BlogPostTagId(this.id, tag);
        BlogPostTag blogPostTag = new BlogPostTag(tagId, this);
        this.blogPostTags.add(blogPostTag);
    }

    // Método para remover una etiqueta
    public void removeEtiqueta(String tag) {
        this.blogPostTags.removeIf(bpt -> bpt.getId().getTag().equals(tag));
    }
}
