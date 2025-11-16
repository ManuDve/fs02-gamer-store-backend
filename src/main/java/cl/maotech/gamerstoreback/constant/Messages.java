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

        private Error() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    private Messages() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
