package controllers.factories;

import controllers.AuthController;
import controllers.ComunidadController;
import controllers.IncidenteDeComunidadController;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoPrestacion;
import models.repositorios.RepoUsuario;

public class FactoryController {
    public static Object controller(String nombre) {
        return switch (nombre) {
            case "Incidente de comunidad" -> new IncidenteDeComunidadController(
                    new RepoComunidad(),
                    new RepoPrestacion()
            );
            case "Comunidad" -> new ComunidadController(new RepoComunidad());
            case "Auth" -> new AuthController(new RepoUsuario());
            default -> null;
        };
    }
}
