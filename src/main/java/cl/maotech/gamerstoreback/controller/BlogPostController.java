package cl.maotech.gamerstoreback.controller;

import cl.maotech.gamerstoreback.constant.BlogPostEndpoints;
import cl.maotech.gamerstoreback.constant.Messages;
import cl.maotech.gamerstoreback.dto.BlogPostResponseDto;
import cl.maotech.gamerstoreback.dto.MessageResponse;
import cl.maotech.gamerstoreback.model.BlogPost;
import cl.maotech.gamerstoreback.service.BlogPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BlogPostEndpoints.BASE)
@RequiredArgsConstructor
@Tag(name = SwaggerMessages.BlogPost.TAG_NAME, description = SwaggerMessages.BlogPost.TAG_DESCRIPTION)
public class BlogPostController {

    private final BlogPostService blogPostService;

    @Operation(summary = SwaggerMessages.BlogPost.GET_ALL_SUMMARY, description = SwaggerMessages.BlogPost.GET_ALL_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200)
    })
    @GetMapping
    public ResponseEntity<List<BlogPostResponseDto>> getAllBlogPosts() {
        return ResponseEntity.ok(blogPostService.getAllBlogPosts());
    }

    @Operation(summary = SwaggerMessages.BlogPost.GET_BY_ID_SUMMARY, description = SwaggerMessages.BlogPost.GET_BY_ID_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "404", description = SwaggerMessages.Response.NOT_FOUND_404)
    })
    @GetMapping(BlogPostEndpoints.ID)
    public ResponseEntity<BlogPostResponseDto> getBlogPostById(
            @Parameter(description = SwaggerMessages.BlogPost.ID_PARAM_DESCRIPTION, example = SwaggerMessages.BlogPost.ID_PARAM_EXAMPLE) @PathVariable String id) {
        return ResponseEntity.ok(blogPostService.getBlogPostById(id));
    }

    @Operation(summary = SwaggerMessages.BlogPost.GET_BY_AUTHOR_SUMMARY, description = SwaggerMessages.BlogPost.GET_BY_AUTHOR_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200)
    })
    @GetMapping(BlogPostEndpoints.BY_AUTHOR)
    public ResponseEntity<List<BlogPostResponseDto>> getBlogPostsByAuthor(
            @Parameter(description = SwaggerMessages.BlogPost.AUTHOR_PARAM_DESCRIPTION, example = SwaggerMessages.BlogPost.AUTHOR_PARAM_EXAMPLE) @PathVariable String author) {
        return ResponseEntity.ok(blogPostService.getBlogPostsByAuthor(author));
    }

    @Operation(summary = SwaggerMessages.BlogPost.GET_BY_TAG_SUMMARY, description = SwaggerMessages.BlogPost.GET_BY_TAG_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200)
    })
    @GetMapping(BlogPostEndpoints.BY_TAG)
    public ResponseEntity<List<BlogPostResponseDto>> getBlogPostsByTag(
            @Parameter(description = SwaggerMessages.BlogPost.TAG_PARAM_DESCRIPTION, example = SwaggerMessages.BlogPost.TAG_PARAM_EXAMPLE) @PathVariable String tag) {
        return ResponseEntity.ok(blogPostService.getBlogPostsByTag(tag));
    }

    @Operation(
            summary = SwaggerMessages.BlogPost.CREATE_SUMMARY,
            description = SwaggerMessages.BlogPost.CREATE_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = SwaggerMessages.Response.CREATED_201),
            @ApiResponse(responseCode = "400", description = SwaggerMessages.Response.BAD_REQUEST_400),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403)
    })
    @PostMapping
    public ResponseEntity<BlogPostResponseDto> createBlogPost(@Valid @RequestBody BlogPost blogPost) {
        return ResponseEntity.status(HttpStatus.CREATED).body(blogPostService.createBlogPost(blogPost));
    }

    @Operation(
            summary = SwaggerMessages.BlogPost.UPDATE_SUMMARY,
            description = SwaggerMessages.BlogPost.UPDATE_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "400", description = SwaggerMessages.Response.BAD_REQUEST_400),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403),
            @ApiResponse(responseCode = "404", description = SwaggerMessages.Response.NOT_FOUND_404)
    })
    @PutMapping(BlogPostEndpoints.ID)
    public ResponseEntity<BlogPostResponseDto> updateBlogPost(
            @Parameter(description = SwaggerMessages.BlogPost.ID_PARAM_DESCRIPTION, example = SwaggerMessages.BlogPost.ID_PARAM_EXAMPLE) @PathVariable String id,
            @Valid @RequestBody BlogPost blogPost) {
        return ResponseEntity.ok(blogPostService.updateBlogPost(id, blogPost));
    }

    @Operation(
            summary = SwaggerMessages.BlogPost.DELETE_SUMMARY,
            description = SwaggerMessages.BlogPost.DELETE_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403),
            @ApiResponse(responseCode = "404", description = SwaggerMessages.Response.NOT_FOUND_404)
    })
    @DeleteMapping(BlogPostEndpoints.ID)
    public ResponseEntity<MessageResponse> deleteBlogPost(
            @Parameter(description = SwaggerMessages.BlogPost.ID_PARAM_DESCRIPTION, example = SwaggerMessages.BlogPost.ID_PARAM_EXAMPLE) @PathVariable String id) {
        blogPostService.deleteBlogPost(id);
        return ResponseEntity.ok(new MessageResponse(Messages.BlogPost.DELETED, HttpStatus.OK.value()));
    }
}
