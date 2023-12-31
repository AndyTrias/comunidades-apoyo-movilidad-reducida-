package controllers.factories;

import controllers.*;
import models.repositorios.*;

public class FactoryController {
  public static Object controller(String nombre) {
    return switch (nombre) {
      case "Incidente de comunidad" -> new IncidenteDeComunidadController(
          new RepoComunidad(),
          new RepoPrestacion(),
          new RepoIncidentes()
      );
      case "Comunidad" -> new ComunidadController(
              new RepoComunidad(),
              new RepoPrestacion(),
              new RepoUsuario()
      );
      case "Auth" -> new AuthController(
              new RepoUsuario(),
              new RepoRol(),
              new RepoEntidadPrestadora(),
              new RepoOrganismoDeControl()
      );
      case "Carga masiva" -> new CargaMasivaController(
          new RepoEntidadPrestadora(),
          new RepoOrganismoDeControl()
      );

      case "Servicio" -> new ServiciosController(
          new RepoServicio()
      );

      case "Entidad" -> new EntidadController(
          new RepoEntidad(),
          new RepoEntidadPrestadora(),
          new RepoLocalizacion()
      );

      case "Establecimiento" -> new EstablecimientoController(
          new RepoEstablecimiento(),
          new RepoEntidad()
      );

      case "Revision de incidentes" -> new RevisionDeIncidenteController(
          new RepoIncidentes(),
          new RepoUsuario(),
            new RepoComunidad()
      );
      case "Sugerencia de fusion" -> new ApiServicioController(
          new RepoComunidad(),
          new RepoFusion(),
          new RepoMembresia(),
          new RepoEstablecimiento(),
          new RepoPrestacion(),
          new RepoEntidad()
      );

      case "Administrador de plataforma" -> new CargaManualController(
          new RepoServicio(),
          new RepoEntidad(),
          new RepoEstablecimiento(),
          new RepoEntidadPrestadora(),
          new RepoLocalizacion()
      );

      case "Perfil" -> new PerfilController(
            new RepoUsuario(),
            new RepoLocalizacion()
      );

      case "Home" -> new HomeController(
      );

      case "Informe" -> new InformesController(
          new RepoEntidad(),
          new RepoInformes(),
          new RepoEntidadPrestadora(),
          new RepoOrganismoDeControl()
      );

      case "Intereses" -> new InteresesController(
          new RepoInteres(),
          new RepoUsuario(),
          new RepoEntidad(),
          new RepoServicio()
      );

      case "Organismo De Control" -> new OrganismoDeControlController(
          new RepoOrganismoDeControl(),
          new RepoInformes()
      );

      case "Entidad Prestadora" -> new EntidadPrestadoraController(
          new RepoEntidadPrestadora(),
          new RepoInformes()
      );
      case "Config" -> new ConfigController(
          new RepoConfig()
      );

      default -> null;
    };
  }
}
