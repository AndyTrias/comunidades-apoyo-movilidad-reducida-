package controllers.factories;

import controllers.*;
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
      case "Revision de incidentes" -> new RevisionDeIncidenteController(
          new RepoUsuario()
      );
      default -> null;
    };
  }
}
