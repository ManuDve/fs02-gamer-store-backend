package cl.maotech.gamerstoreback.constant;

public final class AuthorityEndpoints {
    public static final String BASE = "/api/authorities";
    public static final String ID = "/{id}";
    public static final String BY_USER = "/user/{userId}";

    private AuthorityEndpoints() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

