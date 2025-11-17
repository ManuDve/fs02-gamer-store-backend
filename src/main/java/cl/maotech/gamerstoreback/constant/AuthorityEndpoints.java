package cl.maotech.gamerstoreback.constant;

public final class AuthorityEndpoints {
    public static final String BASE = "/api/authorities";
    public static final String ID = "/{id}";
    public static final String BY_USER = "/user/{userId}";
    public static final String FULL_ID = BASE + ID;
    public static final String FULL_BY_USER = BASE + BY_USER;

    private AuthorityEndpoints() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
