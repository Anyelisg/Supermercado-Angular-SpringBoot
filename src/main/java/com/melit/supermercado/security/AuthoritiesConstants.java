package com.melit.supermercado.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String EMPLEADO = "ROLE_EMPLEADO";

    public static final String SUPERVISOR = "ROLE_SUPERVISOR";

    public static final String GERENTE = "ROLE_GERENTE";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
