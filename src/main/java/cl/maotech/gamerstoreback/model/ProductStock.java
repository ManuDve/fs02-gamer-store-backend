package cl.maotech.gamerstoreback.model;

import cl.maotech.gamerstoreback.constant.Messages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStock {

    @Id
    @Column(length = 10)
    private String productId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull(message = Messages.Validation.STOCK_QUANTITY_REQUIRED)
    @Min(value = 0, message = Messages.Validation.STOCK_QUANTITY_NEGATIVE)
    private Integer quantity;
}

