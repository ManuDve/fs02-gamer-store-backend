package cl.maotech.gamerstoreback.constant;

public final class OrderEndpoints {
    public static final String BASE = "/api/orders";
    public static final String ALL = "/all";
    public static final String ORDER_NUMBER = "/{orderNumber}";

    // Rutas completas para SecurityConfig
    public static final String BASE_WITH_WILDCARD = BASE + "/**";
    public static final String FULL_ALL = BASE + ALL;
    public static final String FULL_ORDER_NUMBER = BASE + ORDER_NUMBER;

    private OrderEndpoints() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

