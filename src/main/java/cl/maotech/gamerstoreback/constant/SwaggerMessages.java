package cl.maotech.gamerstoreback.constant;

public final class SwaggerMessages {

    public static final class Auth {
        public static final String TAG_NAME = "Autenticación";
        public static final String TAG_DESCRIPTION = "Endpoints para autenticación de usuarios";
        public static final String LOGIN_SUMMARY = "Iniciar sesión";
        public static final String LOGIN_DESCRIPTION = "Autentica un usuario con email y contraseña, retorna un token JWT";
        public static final String LOGIN_SUCCESS = "Login exitoso, retorna token JWT";
        public static final String LOGIN_UNAUTHORIZED = "Credenciales inválidas";
        public static final String LOGIN_BAD_REQUEST = "Datos de entrada inválidos";

        private Auth() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    public static final class Product {
        public static final String TAG_NAME = "Productos";
        public static final String TAG_DESCRIPTION = "Gestión de productos de la tienda";
        public static final String GET_ALL_SUMMARY = "Obtener todos los productos";
        public static final String GET_ALL_DESCRIPTION = "Retorna la lista completa de productos disponibles";
        public static final String GET_BY_ID_SUMMARY = "Obtener producto por ID";
        public static final String GET_BY_ID_DESCRIPTION = "Retorna un producto específico por su identificador";
        public static final String GET_BY_CATEGORY_SUMMARY = "Obtener productos por categoría";
        public static final String GET_BY_CATEGORY_DESCRIPTION = "Retorna todos los productos de una categoría específica";
        public static final String SEARCH_SUMMARY = "Buscar productos";
        public static final String SEARCH_DESCRIPTION = "Busca productos por nombre";
        public static final String CREATE_SUMMARY = "Crear producto";
        public static final String CREATE_DESCRIPTION = "Crea un nuevo producto. Requiere rol ADMIN";
        public static final String UPDATE_SUMMARY = "Actualizar producto";
        public static final String UPDATE_DESCRIPTION = "Actualiza un producto existente. Requiere rol ADMIN";
        public static final String DELETE_SUMMARY = "Eliminar producto";
        public static final String DELETE_DESCRIPTION = "Elimina un producto. Requiere rol ADMIN";
        public static final String ID_PARAM_DESCRIPTION = "ID del producto";
        public static final String ID_PARAM_EXAMPLE = "GM001";
        public static final String CATEGORY_PARAM_DESCRIPTION = "Categoría del producto";
        public static final String CATEGORY_PARAM_EXAMPLE = "Juego de Mesa";
        public static final String SEARCH_PARAM_DESCRIPTION = "Término de búsqueda";
        public static final String SEARCH_PARAM_EXAMPLE = "catan";

        private Product() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    public static final class BlogPost {
        public static final String TAG_NAME = "Blog Posts";
        public static final String TAG_DESCRIPTION = "Gestión de publicaciones del blog";
        public static final String GET_ALL_SUMMARY = "Obtener todos los blog posts";
        public static final String GET_ALL_DESCRIPTION = "Retorna la lista completa de publicaciones del blog";
        public static final String GET_BY_ID_SUMMARY = "Obtener blog post por ID";
        public static final String GET_BY_ID_DESCRIPTION = "Retorna una publicación específica por su identificador";
        public static final String GET_BY_AUTHOR_SUMMARY = "Obtener blog posts por autor";
        public static final String GET_BY_AUTHOR_DESCRIPTION = "Retorna todas las publicaciones de un autor específico";
        public static final String GET_BY_TAG_SUMMARY = "Obtener blog posts por tag";
        public static final String GET_BY_TAG_DESCRIPTION = "Retorna todas las publicaciones con un tag específico";
        public static final String CREATE_SUMMARY = "Crear blog post";
        public static final String CREATE_DESCRIPTION = "Crea una nueva publicación en el blog. Requiere rol ADMIN";
        public static final String UPDATE_SUMMARY = "Actualizar blog post";
        public static final String UPDATE_DESCRIPTION = "Actualiza una publicación existente. Requiere rol ADMIN";
        public static final String DELETE_SUMMARY = "Eliminar blog post";
        public static final String DELETE_DESCRIPTION = "Elimina una publicación del blog. Requiere rol ADMIN";
        public static final String ID_PARAM_DESCRIPTION = "ID del blog post";
        public static final String ID_PARAM_EXAMPLE = "BP001";
        public static final String AUTHOR_PARAM_DESCRIPTION = "Nombre del autor";
        public static final String AUTHOR_PARAM_EXAMPLE = "Juan Pérez";
        public static final String TAG_PARAM_DESCRIPTION = "Tag de búsqueda";
        public static final String TAG_PARAM_EXAMPLE = "Estrategia";

        private BlogPost() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    public static final class Order {
        public static final String TAG_NAME = "Órdenes";
        public static final String TAG_DESCRIPTION = "Gestión de órdenes de compra";
        public static final String CREATE_SUMMARY = "Crear orden";
        public static final String CREATE_DESCRIPTION = "Crea una nueva orden de compra. Requiere autenticación";
        public static final String GET_USER_ORDERS_SUMMARY = "Obtener órdenes del usuario";
        public static final String GET_USER_ORDERS_DESCRIPTION = "Retorna todas las órdenes del usuario autenticado";
        public static final String GET_ALL_ORDERS_SUMMARY = "Obtener todas las órdenes";
        public static final String GET_ALL_ORDERS_DESCRIPTION = "Retorna todas las órdenes del sistema. Requiere rol ADMIN";
        public static final String GET_BY_NUMBER_SUMMARY = "Obtener orden por número";
        public static final String GET_BY_NUMBER_DESCRIPTION = "Retorna una orden específica por su número";
        public static final String ORDER_NUMBER_PARAM_DESCRIPTION = "Número de la orden";
        public static final String ORDER_NUMBER_PARAM_EXAMPLE = "ORD-20240101-001";

        private Order() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    public static final class User {
        public static final String TAG_NAME = "Usuarios";
        public static final String TAG_DESCRIPTION = "Gestión de usuarios del sistema";
        public static final String GET_ALL_SUMMARY = "Obtener todos los usuarios";
        public static final String GET_ALL_DESCRIPTION = "Retorna la lista completa de usuarios. Requiere rol ADMIN";
        public static final String GET_BY_ID_SUMMARY = "Obtener usuario por ID";
        public static final String GET_BY_ID_DESCRIPTION = "Retorna un usuario específico por su identificador. Requiere rol ADMIN";
        public static final String CREATE_SUMMARY = "Crear usuario";
        public static final String CREATE_DESCRIPTION = "Crea un nuevo usuario en el sistema. Requiere rol ADMIN";
        public static final String UPDATE_SUMMARY = "Actualizar usuario";
        public static final String UPDATE_DESCRIPTION = "Actualiza un usuario existente. Requiere rol ADMIN";
        public static final String DELETE_SUMMARY = "Eliminar usuario";
        public static final String DELETE_DESCRIPTION = "Elimina un usuario del sistema. Requiere rol ADMIN";
        public static final String ID_PARAM_DESCRIPTION = "ID del usuario";
        public static final String ID_PARAM_EXAMPLE = "1";

        private User() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    public static final class Authority {
        public static final String TAG_NAME = "Autoridades";
        public static final String TAG_DESCRIPTION = "Gestión de roles y permisos de usuarios";
        public static final String GET_ALL_SUMMARY = "Obtener todas las autoridades";
        public static final String GET_ALL_DESCRIPTION = "Retorna la lista completa de roles. Requiere rol ADMIN";
        public static final String GET_BY_ID_SUMMARY = "Obtener autoridad por ID";
        public static final String GET_BY_ID_DESCRIPTION = "Retorna una autoridad específica por su identificador. Requiere rol ADMIN";
        public static final String CREATE_SUMMARY = "Crear autoridad";
        public static final String CREATE_DESCRIPTION = "Asigna un nuevo rol a un usuario. Requiere rol ADMIN";
        public static final String DELETE_SUMMARY = "Eliminar autoridad";
        public static final String DELETE_DESCRIPTION = "Elimina un rol de un usuario. Requiere rol ADMIN";
        public static final String UPDATE_SUMMARY = "Actualizar autoridad";
        public static final String UPDATE_DESCRIPTION = "Actualiza el rol de un usuario. Requiere rol ADMIN";
        public static final String ID_PARAM_DESCRIPTION = "ID de la autoridad";
        public static final String ID_PARAM_EXAMPLE = "1";

        private Authority() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    public static final class Response {
        public static final String SUCCESS_200 = "Operación exitosa";
        public static final String CREATED_201 = "Recurso creado exitosamente";
        public static final String BAD_REQUEST_400 = "Datos inválidos";
        public static final String UNAUTHORIZED_401 = "No autenticado";
        public static final String FORBIDDEN_403 = "Sin permisos de administrador";
        public static final String NOT_FOUND_404 = "Recurso no encontrado";

        private Response() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    private SwaggerMessages() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

