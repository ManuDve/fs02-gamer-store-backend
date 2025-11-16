package cl.maotech.gamerstoreback.constant;

public final class UserEndpoints {
    public static final String BASE = "/api/users";
    public static final String ID = "/{id}";

    private UserEndpoints() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
