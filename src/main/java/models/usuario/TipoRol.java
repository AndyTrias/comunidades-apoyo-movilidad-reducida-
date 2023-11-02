package models.usuario;

import io.javalin.security.RouteRole;

public enum TipoRol implements RouteRole {
    ADMINISTRADOR_PLATAFORMA, MIEMBRO, ADMINISTRADOR_COMUNIDAD, ENTIDAD_PRESTADORA, ORGANISMO_DE_CONTROL
}
