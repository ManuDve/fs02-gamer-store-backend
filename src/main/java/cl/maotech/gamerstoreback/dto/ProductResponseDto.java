package cl.maotech.gamerstoreback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private String id;
    private String category;
    private String name;
    private Integer price;
    private String img;
    private String descripcion;
    private String reseña;
    private Map<String, String> caracteristics;
}