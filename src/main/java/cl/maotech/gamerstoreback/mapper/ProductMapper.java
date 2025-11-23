package cl.maotech.gamerstoreback.mapper;

import cl.maotech.gamerstoreback.dto.ProductResponseDto;
import cl.maotech.gamerstoreback.model.Product;
import cl.maotech.gamerstoreback.model.ProductCharacteristic;

import java.util.HashMap;
import java.util.Map;

public class ProductMapper {

    public static ProductResponseDto toResponseDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setCategory(product.getCategory());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setImg(product.getImg());
        dto.setDescription(product.getDescription());
        dto.setReview(product.getReview());
        dto.setCharacteristics(mapCharacteristics(product.getCharacteristics(), product.getCategory()));
        dto.setStock(product.getStock() != null ? product.getStock().getQuantity() : 0);
        return dto;
    }

    private static Map<String, String> mapCharacteristics(ProductCharacteristic chars, String category) {
        Map<String, String> map = new HashMap<>();
        if (chars == null) {
            return map;
        }

        if ("Juego de Mesa".equals(category)) {
            addIfNotNull(map, "jugadores", chars.getJugadores());
            addIfNotNull(map, "edad_minima", chars.getEdadMinima());
            addIfNotNull(map, "tiempo_juego", chars.getTiempoJuego());
        } else if ("Periférico Gamer".equals(category)) {
            addIfNotNull(map, "tipo_switch", chars.getTipoSwitch());
            addIfNotNull(map, "iluminacion", chars.getIluminacion());
            addIfNotNull(map, "conexión", chars.getConexion());
            addIfNotNull(map, "sensor", chars.getSensor());
            addIfNotNull(map, "peso", chars.getPeso());
            addIfNotNull(map, "sonido", chars.getSonido());
            addIfNotNull(map, "compatibilidad", chars.getCompatibilidad());
            addIfNotNull(map, "dimensiones", chars.getDimensiones());
            addIfNotNull(map, "material", chars.getMaterial());
            addIfNotNull(map, "bordes", chars.getBordes());
            addIfNotNull(map, "plataforma", chars.getPlataforma());
            addIfNotNull(map, "botones", chars.getBotones());
            addIfNotNull(map, "bateria", chars.getBateria());
            addIfNotNull(map, "resolucion", chars.getResolucion());
            addIfNotNull(map, "fps", chars.getFps());
            addIfNotNull(map, "microfono", chars.getMicrofono());
            addIfNotNull(map, "rotacion", chars.getRotacion());
            addIfNotNull(map, "feedback", chars.getFeedback());
            addIfNotNull(map, "diseno", chars.getDiseno());
            addIfNotNull(map, "ajuste", chars.getAjuste());
            addIfNotNull(map, "soporte", chars.getSoporte());
            addIfNotNull(map, "tipo", chars.getTipo());
            addIfNotNull(map, "patron", chars.getPatron());
            addIfNotNull(map, "tamano", chars.getTamano());
            addIfNotNull(map, "frecuencia", chars.getFrecuencia());
            addIfNotNull(map, "respuesta", chars.getRespuesta());
        } else if ("Consola".equals(category)) {
            addIfNotNull(map, "resolucion_maxima", chars.getResolucionMaxima());
            addIfNotNull(map, "almacenamiento", chars.getAlmacenamiento());
            addIfNotNull(map, "lector_discos", chars.getLectorDiscos());
            addIfNotNull(map, "pantalla", chars.getPantalla());
            addIfNotNull(map, "modo_juego", chars.getModoJuego());
            addIfNotNull(map, "memoria", chars.getMemoria());
            addIfNotNull(map, "caracteristica_clave", chars.getCaracteristicaClave());
            addIfNotNull(map, "color", chars.getColor());
            addIfNotNull(map, "genero", chars.getGenero());
            addIfNotNull(map, "clasificacion", chars.getClasificacion());
            addIfNotNull(map, "jugadores", chars.getJugadores2());
        }

        return map;
    }

    private static void addIfNotNull(Map<String, String> map, String key, String value) {
        if (value != null && !value.isEmpty()) {
            map.put(key, value);
        }
    }

    private ProductMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
