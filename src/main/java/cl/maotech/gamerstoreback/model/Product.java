package cl.maotech.gamerstoreback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(length = 10)
    private String id;

    @NotBlank(message = "La categoría es requerida")
    @Column(nullable = false)
    private String category;

    @NotBlank(message = "El nombre es requerido")
    @Column(nullable = false, length = 500)
    private String name;

    @NotNull(message = "El precio es requerido")
    @Column(nullable = false)
    private Integer price;

    @Column(length = 1000)
    private String img;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String review;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ProductCharacteristic characteristics;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ProductStock stock;

    public void setCharacteristics(ProductCharacteristic characteristics) {
        this.characteristics = characteristics;
        if (characteristics != null) {
            characteristics.setProduct(this);
        }
    }

    public void setStock(ProductStock stock) {
        this.stock = stock;
        if (stock != null) {
            stock.setProduct(this);
        }
    }
}
