package cl.maotech.gamerstoreback.constant;

public final class Messages {

    public static final class User {
        public static final String NOT_FOUND = "Usuario no encontrado con id: ";
        public static final String USERNAME_NOT_FOUND = "Usuario no encontrado: ";
        public static final String EMAIL_ALREADY_EXISTS = "El email ya está registrado: ";
        public static final String NAME_REQUIRED = "El nombre no puede estar vacío";
        public static final String EMAIL_REQUIRED = "El email no puede estar vacío";
        public static final String EMAIL_INVALID = "El email debe ser válido";
        public static final String PASSWORD_REQUIRED = "La contraseña no puede estar vacía";
        public static final String PHONE_REQUIRED = "El teléfono no puede estar vacío";
        public static final String DELETED = "Usuario eliminado exitosamente";

        private User() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    public static final class Authority {
        public static final String NOT_FOUND = "Autoridad no encontrada con id: ";
        public static final String USER_NOT_FOUND = "Usuario no encontrado con id: ";
        public static final String AUTHORITY_REQUIRED = "El nombre de la autoridad no puede estar vacío";
        public static final String CREATED = "Autoridad creada exitosamente";
        public static final String DELETED = "Autoridad eliminada exitosamente";

        private Authority() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    public static final class Error {
        public static final String INTERNAL_SERVER_ERROR = "Error interno del servidor";
        public static final String DATA_INTEGRITY_ERROR = "Error de integridad de datos";
        public static final String EMAIL_ALREADY_REGISTERED = "El email ya está registrado";
        public static final String INVALID_CREDENTIALS = "Credenciales inválidas";
        public static final String BAD_CREDENTIALS = "Email o contraseña incorrectos";
        public static final String AUTHENTICATION_FAILED = "Error de autenticación";
        public static final String INVALID_REQUEST_BODY = "El cuerpo de la petición es inválido o está vacío";
        public static final String MALFORMED_JSON = "El formato JSON es inválido";
        public static final String ACCESS_DENIED = "No tienes permisos para realizar esta acción";

        private Error() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    public static final class Product {
        public static final String NOT_FOUND = "Producto no encontrado con id: ";
        public static final String INSUFFICIENT_STOCK = "Stock insuficiente para el producto: ";
        public static final String NO_STOCK = "El producto no tiene stock disponible: ";
        public static final String STOCK_EXCEEDED = "La cantidad solicitada excede el stock disponible";
        public static final String STOCK_AVAILABLE = ". Disponible: ";
        public static final String STOCK_REQUESTED = ", Solicitado: ";
        public static final String CREATED = "Producto creado exitosamente";
        public static final String UPDATED = "Producto actualizado exitosamente";
        public static final String DELETED = "Producto eliminado exitosamente";

        private Product() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    public static final class Order {
        public static final String INVALID_TOTAL = "El total calculado no coincide con el total enviado";
        public static final String INVALID_SUBTOTAL = "El subtotal no coincide. Calculado: ";
        public static final String INVALID_PRICE = "El precio del producto no coincide";
        public static final String EMPTY_ITEMS = "La orden debe contener al menos un producto";
        public static final String CREATED = "Orden creada exitosamente";
        public static final String NOT_FOUND = "Orden no encontrada con número: ";
        public static final String UNAUTHORIZED_ACCESS = "No tienes permisos para ver esta orden";
        public static final String STATUS_COMPLETED = "COMPLETADA";

        private Order() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    public static final class BlogPost {
        public static final String NOT_FOUND = "Blog post no encontrado con id: ";
        public static final String CREATED = "Blog post creado exitosamente";
        public static final String UPDATED = "Blog post actualizado exitosamente";
        public static final String DELETED = "Blog post eliminado exitosamente";

        private BlogPost() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    public static final class Validation {
        public static final String ORDER_NULL = "La orden no puede ser nula";
        public static final String SHIPPING_ADDRESS_REQUIRED = "La dirección de envío es requerida";
        public static final String ITEMS_REQUIRED = "Los items son requeridos";
        public static final String ITEMS_NOT_EMPTY = "Debe incluir al menos un producto";
        public static final String PAYMENT_REQUIRED = "La información de pago es requerida";
        public static final String SHIPPING_COST_REQUIRED = "El costo de envío es requerido";
        public static final String SHIPPING_COST_NEGATIVE = "El costo de envío no puede ser negativo";
        public static final String ADDRESS_REQUIRED = "La dirección es requerida";
        public static final String CITY_REQUIRED = "La ciudad es requerida";
        public static final String STATE_REQUIRED = "El estado es requerido";
        public static final String ZIP_CODE_REQUIRED = "El código postal es requerido";
        public static final String PRODUCT_ID_REQUIRED = "El ID del producto es requerido";
        public static final String QUANTITY_REQUIRED = "La cantidad es requerida";
        public static final String QUANTITY_MIN = "La cantidad debe ser al menos 1";
        public static final String CARD_NUMBER_REQUIRED = "El número de tarjeta es requerido";
        public static final String CARD_NAME_REQUIRED = "El nombre en la tarjeta es requerido";
        public static final String CARD_EXPIRY_REQUIRED = "La fecha de expiración es requerida";
        public static final String CARD_CVV_REQUIRED = "El CVV es requerido";
        public static final String CATEGORY_REQUIRED = "La categoría es requerida";
        public static final String NAME_REQUIRED = "El nombre es requerido";
        public static final String PRICE_REQUIRED = "El precio es requerido";
        public static final String TITLE_REQUIRED = "El título es requerido";
        public static final String AUTHOR_REQUIRED = "El autor es requerido";
        public static final String EMAIL_NOT_EMPTY = "El email no puede estar vacío";
        public static final String EMAIL_VALID = "El email debe ser válido";
        public static final String PASSWORD_NOT_EMPTY = "La contraseña no puede estar vacía";
        public static final String STOCK_QUANTITY_REQUIRED = "La cantidad es requerida";
        public static final String STOCK_QUANTITY_NEGATIVE = "La cantidad no puede ser negativa";

        private Validation() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    private Messages() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
