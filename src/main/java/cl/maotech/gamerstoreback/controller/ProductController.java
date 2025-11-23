package cl.maotech.gamerstoreback.controller;

import cl.maotech.gamerstoreback.constant.Messages;
import cl.maotech.gamerstoreback.constant.ProductEndpoints;
import cl.maotech.gamerstoreback.constant.SwaggerMessages;
import cl.maotech.gamerstoreback.dto.MessageResponse;
import cl.maotech.gamerstoreback.dto.ProductResponseDto;
import cl.maotech.gamerstoreback.model.Product;
import cl.maotech.gamerstoreback.service.ProductService;
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
@RequestMapping(ProductEndpoints.BASE)
@RequiredArgsConstructor
@Tag(name = SwaggerMessages.Product.TAG_NAME, description = SwaggerMessages.Product.TAG_DESCRIPTION)
public class ProductController {

    private final ProductService productService;

    @Operation(summary = SwaggerMessages.Product.GET_ALL_SUMMARY, description = SwaggerMessages.Product.GET_ALL_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200)
    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = SwaggerMessages.Product.GET_BY_ID_SUMMARY, description = SwaggerMessages.Product.GET_BY_ID_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "404", description = SwaggerMessages.Response.NOT_FOUND_404)
    })
    @GetMapping(ProductEndpoints.ID)
    public ResponseEntity<ProductResponseDto> getProductById(
            @Parameter(description = SwaggerMessages.Product.ID_PARAM_DESCRIPTION, example = SwaggerMessages.Product.ID_PARAM_EXAMPLE) @PathVariable String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = SwaggerMessages.Product.GET_BY_CATEGORY_SUMMARY, description = SwaggerMessages.Product.GET_BY_CATEGORY_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200)
    })
    @GetMapping(ProductEndpoints.BY_CATEGORY)
    public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(
            @Parameter(description = SwaggerMessages.Product.CATEGORY_PARAM_DESCRIPTION, example = SwaggerMessages.Product.CATEGORY_PARAM_EXAMPLE) @PathVariable String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @Operation(summary = SwaggerMessages.Product.SEARCH_SUMMARY, description = SwaggerMessages.Product.SEARCH_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200)
    })
    @GetMapping(ProductEndpoints.SEARCH)
    public ResponseEntity<List<ProductResponseDto>> searchProducts(
            @Parameter(description = SwaggerMessages.Product.SEARCH_PARAM_DESCRIPTION, example = SwaggerMessages.Product.SEARCH_PARAM_EXAMPLE) @RequestParam String name) {
        return ResponseEntity.ok(productService.searchProducts(name));
    }

    @Operation(
            summary = SwaggerMessages.Product.CREATE_SUMMARY,
            description = SwaggerMessages.Product.CREATE_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = SwaggerMessages.Response.CREATED_201),
            @ApiResponse(responseCode = "400", description = SwaggerMessages.Response.BAD_REQUEST_400),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403)
    })
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    @Operation(
            summary = SwaggerMessages.Product.UPDATE_SUMMARY,
            description = SwaggerMessages.Product.UPDATE_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "400", description = SwaggerMessages.Response.BAD_REQUEST_400),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403),
            @ApiResponse(responseCode = "404", description = SwaggerMessages.Response.NOT_FOUND_404)
    })
    @PutMapping(ProductEndpoints.ID)
    public ResponseEntity<ProductResponseDto> updateProduct(
            @Parameter(description = SwaggerMessages.Product.ID_PARAM_DESCRIPTION, example = SwaggerMessages.Product.ID_PARAM_EXAMPLE) @PathVariable String id,
            @Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @Operation(
            summary = SwaggerMessages.Product.DELETE_SUMMARY,
            description = SwaggerMessages.Product.DELETE_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403),
            @ApiResponse(responseCode = "404", description = SwaggerMessages.Response.NOT_FOUND_404)
    })
    @DeleteMapping(ProductEndpoints.ID)
    public ResponseEntity<MessageResponse> deleteProduct(
            @Parameter(description = SwaggerMessages.Product.ID_PARAM_DESCRIPTION, example = SwaggerMessages.Product.ID_PARAM_EXAMPLE) @PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new MessageResponse(Messages.Product.DELETED, HttpStatus.OK.value()));
    }
}