package cl.maotech.gamerstoreback.constant;

public final class BlogPostEndpoints {
    public static final String BASE = "/api/blog-posts";
    public static final String ID = "/{id}";
    public static final String BY_TAG = "/tag/{tag}";
    public static final String BY_AUTHOR = "/author/{author}";

    // Rutas completas para SecurityConfig
    public static final String BASE_WITH_WILDCARD = BASE + "/**";
    public static final String FULL_ID = BASE + ID;

    private BlogPostEndpoints() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}