package cl.maotech.gamerstoreback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_characteristics")
public class ProductCharacteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    // Características para Juegos de Mesa
    private String jugadores;
    @Column(name = "edad_minima")
    private String edadMinima;
    @Column(name = "tiempo_juego")
    private String tiempoJuego;

    // Características para Periféricos
    @Column(name = "tipo_switch")
    private String tipoSwitch;
    private String iluminacion;
    private String conexion;
    private String sensor;
    private String peso;
    private String sonido;
    private String compatibilidad;
    private String dimensiones;
    private String material;
    private String bordes;
    private String plataforma;
    private String botones;
    private String bateria;
    private String resolucion;
    private String fps;
    private String microfono;
    private String rotacion;
    private String feedback;
    private String diseño;
    private String ajuste;
    private String soporte;
    private String tipo;
    private String patron;
    private String tamaño;
    private String frecuencia;
    private String respuesta;

    // Características para Consolas
    @Column(name = "resolucion_maxima")
    private String resolucionMaxima;
    private String almacenamiento;
    @Column(name = "lector_discos")
    private String lectorDiscos;
    private String pantalla;
    @Column(name = "modo_juego")
    private String modoJuego;
    private String memoria;
    @Column(name = "caracteristica_clave")
    private String caracteristicaClave;
    private String color;
    private String genero;
    private String clasificacion;
    private String jugadores2;
}