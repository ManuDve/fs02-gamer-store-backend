package cl.maotech.gamerstoreback.service;

import cl.maotech.gamerstoreback.constant.Messages;
import cl.maotech.gamerstoreback.dto.ProductResponseDto;
import cl.maotech.gamerstoreback.exception.ResourceNotFoundException;
import cl.maotech.gamerstoreback.mapper.ProductMapper;
import cl.maotech.gamerstoreback.model.Product;
import cl.maotech.gamerstoreback.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.Product.NOT_FOUND + id));
        return ProductMapper.toResponseDto(product);
    }

    public List<ProductResponseDto> getProductsByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .map(ProductMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(ProductMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponseDto createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toResponseDto(savedProduct);
    }

    @Transactional
    public ProductResponseDto updateProduct(String id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.Product.NOT_FOUND + id));

        existingProduct.setCategory(product.getCategory());
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setImg(product.getImg());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setReview(product.getReview());
        existingProduct.setCharacteristics(product.getCharacteristics());

        if (product.getStock() != null) {
            existingProduct.setStock(product.getStock());
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return ProductMapper.toResponseDto(updatedProduct);
    }

    @Transactional
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException(Messages.Product.NOT_FOUND + id);
        }
        productRepository.deleteById(id);
    }
}