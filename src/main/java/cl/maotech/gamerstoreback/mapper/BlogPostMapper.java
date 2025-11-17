package cl.maotech.gamerstoreback.mapper;

import cl.maotech.gamerstoreback.dto.BlogPostResponseDto;
import cl.maotech.gamerstoreback.model.BlogPost;

public class BlogPostMapper {

    public static BlogPostResponseDto toResponseDto(BlogPost blogPost) {
        BlogPostResponseDto dto = new BlogPostResponseDto();
        dto.setId(blogPost.getId());
        dto.setTitulo(blogPost.getTitulo());
        dto.setAutor(blogPost.getAutor());
        dto.setFecha(blogPost.getFecha());
        dto.setImagen(blogPost.getImagen());
        dto.setDescripcion(blogPost.getDescripcion());
        dto.setArticulo(blogPost.getArticulo());
        dto.setEtiquetas(blogPost.getEtiquetas());
        return dto;
    }

    private BlogPostMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}