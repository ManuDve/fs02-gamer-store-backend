package cl.maotech.gamerstoreback.service;

import cl.maotech.gamerstoreback.dto.BlogPostResponseDto;
import cl.maotech.gamerstoreback.exception.ResourceNotFoundException;
import cl.maotech.gamerstoreback.mapper.BlogPostMapper;
import cl.maotech.gamerstoreback.model.BlogPost;
import cl.maotech.gamerstoreback.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public List<BlogPostResponseDto> getAllBlogPosts() {
        return blogPostRepository.findAll().stream()
                .map(BlogPostMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public BlogPostResponseDto getBlogPostById(String id) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog post no encontrado con id: " + id));
        return BlogPostMapper.toResponseDto(blogPost);
    }

    public List<BlogPostResponseDto> getBlogPostsByAuthor(String autor) {
        return blogPostRepository.findByAutor(autor).stream()
                .map(BlogPostMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<BlogPostResponseDto> getBlogPostsByTag(String tag) {
        return blogPostRepository.findByTag(tag).stream()
                .map(BlogPostMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BlogPostResponseDto createBlogPost(BlogPost blogPost) {
        BlogPost savedBlogPost = blogPostRepository.save(blogPost);
        return BlogPostMapper.toResponseDto(savedBlogPost);
    }

    @Transactional
    public BlogPostResponseDto updateBlogPost(String id, BlogPost blogPost) {
        BlogPost existingBlogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog post no encontrado con id: " + id));

        existingBlogPost.setTitulo(blogPost.getTitulo());
        existingBlogPost.setAutor(blogPost.getAutor());
        existingBlogPost.setFecha(blogPost.getFecha());
        existingBlogPost.setImagen(blogPost.getImagen());
        existingBlogPost.setDescripcion(blogPost.getDescripcion());
        existingBlogPost.setArticulo(blogPost.getArticulo());
        existingBlogPost.setEtiquetas(blogPost.getEtiquetas());

        BlogPost updatedBlogPost = blogPostRepository.save(existingBlogPost);
        return BlogPostMapper.toResponseDto(updatedBlogPost);
    }

    @Transactional
    public void deleteBlogPost(String id) {
        if (!blogPostRepository.existsById(id)) {
            throw new ResourceNotFoundException("Blog post no encontrado con id: " + id);
        }
        blogPostRepository.deleteById(id);
    }
}

