package models.comunidades;

import io.javalin.security.RouteRole;

public enum TipoRol implements RouteRole {
    ADMINISTRADOR_PLATAFORMA, MIEMBRO, ADMINISTRADOR_COMUNIDAD
}
