package controllers.factories;

import controllers.AuthController;
import controllers.CargaMasivaController;
import controllers.ComunidadController;
import controllers.IncidenteDeComunidadController;
import models.repositorios.*;

public class FactoryController {
    public static Object controller(String nombre) {
        return switch (nombre) {
            case "Incidente de comunidad" -> new IncidenteDeComunidadController(
                    new RepoComunidad(),
                    new RepoPrestacion(),
                    new RepoUsuario()
            );
            case "Comunidad" -> new ComunidadController(new RepoComunidad());
            case "Auth" -> new AuthController(new RepoUsuario());
            case "Carga masiva" -> new CargaMasivaController(
                    new RepoEntidadPrestadora(),
                    new RepoOrganismoDeControl()
            );
            default -> null;
        };
    }
}
