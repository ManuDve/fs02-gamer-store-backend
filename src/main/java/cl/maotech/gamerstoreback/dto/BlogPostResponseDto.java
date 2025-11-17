
package cl.maotech.gamerstoreback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostResponseDto {
    private String id;
    private String titulo;
    private String autor;
    private LocalDate fecha;
    private String imagen;
    private String descripcion;
    private String articulo;
    private List<String> etiquetas;
}
