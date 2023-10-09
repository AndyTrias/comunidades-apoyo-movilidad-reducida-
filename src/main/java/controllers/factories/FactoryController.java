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
      case "Comunidad" -> new ComunidadController(
              new RepoComunidad(),
              new RepoUsuario()
      );
      case "Auth" -> new AuthController(
              new RepoUsuario()
      );
      case "Carga masiva" -> new CargaMasivaController(
          new RepoEntidadPrestadora(),
          new RepoOrganismoDeControl()
      );
      case "Revision de incidentes" -> new RevisionDeIncidenteController(
          new RepoUsuario()
      );
      case "Sugerencia de fusion" -> new ApiServicioController(
          new RepoComunidad(),
          new RepoFusion(),
          new RepoMembresia(),
          new RepoEstablecimiento(),
          new RepoPrestacion(),
          new RepoEntidad()
      );

      case "Administrador de plataforma" -> new AdminController(
          new RepoServicio(),
          new RepoEntidad(),
          new RepoEstablecimiento()
      );

      default -> null;
    };
  }
}
