package cl.maotech.gamerstoreback.constant;

public final class SwaggerEndpoints {

    public static final String SWAGGER_UI = "/swagger-ui/**";
    public static final String SWAGGER_UI_HTML = "/swagger-ui.html";
    public static final String API_DOCS = "/v3/api-docs/**";
    public static final String API_DOCS_YAML = "/v3/api-docs.yaml";

    private SwaggerEndpoints() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final class OpenApiInfo {
        public static final String TITLE = "GamerStore API";
        public static final String VERSION = "1.0.0";
        public static final String DESCRIPTION = "API RESTful para la gestión de una tienda de productos gaming. Incluye gestión de inventario, stock, órdenes de compra, blog posts y autenticación JWT.";
        public static final String CONTACT_NAME = "MaoTech";
        public static final String CONTACT_EMAIL = "support@maotech.cl";
        public static final String LICENSE_NAME = "Apache 2.0";
        public static final String LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";
        public static final String SERVER_URL = "http://localhost:8080";
        public static final String SERVER_DESCRIPTION = "Servidor de desarrollo local";
        public static final String SECURITY_SCHEME_NAME = "bearerAuth";
        public static final String SECURITY_SCHEME = "bearer";
        public static final String BEARER_FORMAT = "JWT";
        public static final String SECURITY_DESCRIPTION = "Ingrese el token JWT obtenido del endpoint de login";

        private OpenApiInfo() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }
}
