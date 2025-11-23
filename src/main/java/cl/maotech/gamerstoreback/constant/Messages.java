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
        public static final String CREATED = "Producto creado exitosamente";
        public static final String DELETED = "Producto eliminado exitosamente";

        private Product() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    public static final class Order {
        public static final String INVALID_TOTAL = "El total calculado no coincide con el total enviado";
        public static final String EMPTY_ITEMS = "La orden debe contener al menos un producto";
        public static final String CREATED = "Orden creada exitosamente";
        public static final String NOT_FOUND = "Orden no encontrada con número: ";

        private Order() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    private Messages() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
