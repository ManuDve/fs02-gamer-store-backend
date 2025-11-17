package cl.maotech.gamerstoreback.constant;

public final class ProductEndpoints {
    public static final String BASE = "/api/products";
    public static final String ID = "/{id}";
    public static final String BY_CATEGORY = "/category/{category}";
    public static final String SEARCH = "/search";

    // Rutas completas para SecurityConfig
    public static final String BASE_WITH_WILDCARD = BASE + "/**";
    public static final String FULL_ID = BASE + ID;

    private ProductEndpoints() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
