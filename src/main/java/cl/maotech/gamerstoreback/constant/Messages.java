package cl.maotech.gamerstoreback.constant;

public final class Messages {

    public static final class User {
        public static final String NOT_FOUND = "Usuario no encontrado con id: ";
        public static final String EMAIL_ALREADY_EXISTS = "El email ya está registrado: ";
        public static final String NAME_REQUIRED = "El nombre no puede estar vacío";
        public static final String EMAIL_REQUIRED = "El email no puede estar vacío";
        public static final String EMAIL_INVALID = "El email debe ser válido";
        public static final String PASSWORD_REQUIRED = "La contraseña no puede estar vacía";
        public static final String PHONE_REQUIRED = "El teléfono no puede estar vacío";

        private User() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    public static final class Error {
        public static final String INTERNAL_SERVER_ERROR = "Error interno del servidor";

        private Error() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    private Messages() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
