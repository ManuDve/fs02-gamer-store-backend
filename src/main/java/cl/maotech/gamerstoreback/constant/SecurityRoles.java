package cl.maotech.gamerstoreback.constant;

public final class SecurityRoles {

    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    private SecurityRoles() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
