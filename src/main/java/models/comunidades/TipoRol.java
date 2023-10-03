package models.comunidades;

import io.javalin.security.RouteRole;

public enum TipoRol implements RouteRole {
    ADMINISTRADOR, MIEMBRO
}
