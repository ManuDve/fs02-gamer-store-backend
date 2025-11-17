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
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String reseña;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductCharacteristic caracteristics;

    public void setCaracteristics(ProductCharacteristic caracteristics) {
        this.caracteristics = caracteristics;
        if (caracteristics != null) {
            caracteristics.setProduct(this);
        }
    }
}

