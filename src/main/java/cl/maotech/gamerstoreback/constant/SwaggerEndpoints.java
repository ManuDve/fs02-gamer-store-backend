package cl.maotech.gamerstoreback.constant;

public final class SwaggerEndpoints {

    public static final String SWAGGER_UI = "/swagger-ui/**";
    public static final String SWAGGER_UI_HTML = "/swagger-ui.html";
    public static final String API_DOCS = "/v3/api-docs/**";
    public static final String API_DOCS_YAML = "/v3/api-docs.yaml";

    private SwaggerEndpoints() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

