package cl.maotech.gamerstoreback.controller;

import cl.maotech.gamerstoreback.constant.BlogPostEndpoints;
import cl.maotech.gamerstoreback.constant.Messages;
import cl.maotech.gamerstoreback.dto.BlogPostResponseDto;
import cl.maotech.gamerstoreback.dto.MessageResponse;
import cl.maotech.gamerstoreback.model.BlogPost;
import cl.maotech.gamerstoreback.service.BlogPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BlogPostEndpoints.BASE)
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostService blogPostService;

    @GetMapping
    public ResponseEntity<List<BlogPostResponseDto>> getAllBlogPosts() {
        return ResponseEntity.ok(blogPostService.getAllBlogPosts());
    }

    @GetMapping(BlogPostEndpoints.ID)
    public ResponseEntity<BlogPostResponseDto> getBlogPostById(@PathVariable String id) {
        return ResponseEntity.ok(blogPostService.getBlogPostById(id));
    }

    @GetMapping(BlogPostEndpoints.BY_AUTHOR)
    public ResponseEntity<List<BlogPostResponseDto>> getBlogPostsByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(blogPostService.getBlogPostsByAuthor(author));
    }

    @GetMapping(BlogPostEndpoints.BY_TAG)
    public ResponseEntity<List<BlogPostResponseDto>> getBlogPostsByTag(@PathVariable String tag) {
        return ResponseEntity.ok(blogPostService.getBlogPostsByTag(tag));
    }

    @PostMapping
    public ResponseEntity<BlogPostResponseDto> createBlogPost(@Valid @RequestBody BlogPost blogPost) {
        return ResponseEntity.status(HttpStatus.CREATED).body(blogPostService.createBlogPost(blogPost));
    }

    @PutMapping(BlogPostEndpoints.ID)
    public ResponseEntity<BlogPostResponseDto> updateBlogPost(@PathVariable String id, @Valid @RequestBody BlogPost blogPost) {
        return ResponseEntity.ok(blogPostService.updateBlogPost(id, blogPost));
    }

    @DeleteMapping(BlogPostEndpoints.ID)
    public ResponseEntity<MessageResponse> deleteBlogPost(@PathVariable String id) {
        blogPostService.deleteBlogPost(id);
        return ResponseEntity.ok(new MessageResponse(Messages.BlogPost.DELETED, HttpStatus.OK.value()));
    }
}
