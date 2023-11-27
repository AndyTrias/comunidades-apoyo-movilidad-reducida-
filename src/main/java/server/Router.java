package server;

import controllers.*;
import controllers.factories.FactoryController;
import controllers.HomeController;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.Javalin;
import models.usuario.TipoRol;
import server.exceptions.*;


import static io.javalin.apibuilder.ApiBuilder.path;

public class Router implements WithSimplePersistenceUnit {

  public Router() {
    init();
  }

  public void init() {
    Javalin app = Server.app();

    app.error(404, ctx -> {
      throw new PaginaNoEncontradaException("No se encontro la pagina solicitada");
    });

    app.error(500, ctx -> {
      throw new ServerErrorException("F");
    });


    app.routes(() -> {

      app.get("comunidades/create", ((ComunidadController) FactoryController.controller("Comunidad"))::create);
      app.get("comunidades", ((ComunidadController) FactoryController.controller("Comunidad"))::index);
      app.post("comunidades", ((ComunidadController) FactoryController.controller("Comunidad"))::save);

      app.post("comunidades/unirse", ((ComunidadController) FactoryController.controller("Comunidad"))::unir);

      app.post("comunidades/{id}/prestacion", ((ComunidadController) FactoryController.controller("Comunidad"))::agregarPrestacion);
      app.get("comunidades/{id}/prestaciones", ((ComunidadController) FactoryController.controller("Comunidad"))::mostrarPrestaciones);
      app.post("comunidades/{id}/prestaciones", ((ComunidadController) FactoryController.controller("Comunidad"))::afectarPrestaciones);

      app.get("comunidades/{id}", ((ComunidadController) FactoryController.controller("Comunidad"))::show);
      path("comunidades/{id}/incidentes", () -> {
        app.get("comunidades/{id}/incidentes/create", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::create);
        app.get("comunidades/{id}/incidentes/{id_incidente}", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::show);
        app.post("comunidades/{id}/incidentes", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::save);
        app.post("comunidades/{id}/incidentes/{id_incidente}/cerrar", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::cerrarIncidente);

      });
    });

    app.routes(() -> {
      app.get("login", ((AuthController) FactoryController.controller("Auth"))::showLogin);
      app.get("logout", ((AuthController) FactoryController.controller("Auth"))::logout);
      app.get("register", ((AuthController) FactoryController.controller("Auth"))::showRegister);
      app.post("login", ((AuthController) FactoryController.controller("Auth"))::login);
      app.post("register", ((AuthController) FactoryController.controller("Auth"))::register);
    });

    app.routes(() -> {
      app.post("revisionDeIncidentes", ((RevisionDeIncidenteController) FactoryController.controller("Revision de incidentes"))::postUbicacionExacta);
      app.get("revisionDeIncidentes", ((RevisionDeIncidenteController) FactoryController.controller("Revision de incidentes"))::show);
      app.get("revisionDeIncidentes/{id}", ((RevisionDeIncidenteController) FactoryController.controller("Revision de incidentes"))::showIncidente);
      app.post("revisionDeIncidentes/{id}", ((RevisionDeIncidenteController) FactoryController.controller("Revision de incidentes"))::resolucionDeIncidente);
    });

    app.routes(() -> {
      app.post("sugerenciaDeFusion", ((ApiServicioController) FactoryController.controller("Sugerencia de fusion"))::fusionDeComunidades);
    });

    app.routes(() -> {
      app.get("admin/cargaManual", ((CargaManualController) FactoryController.controller("Administrador de plataforma"))::cargaManual, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.get("admin/cargaManual/seleccionarUbicacion", ((CargaManualController) FactoryController.controller("Administrador de plataforma"))::seleccionarUbicacion, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.get("admin/cargaManual/seleccionarMunicipio", ((CargaManualController) FactoryController.controller("Administrador de plataforma"))::seleccionarMunicipio, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/entidad", ((CargaManualController) FactoryController.controller("Administrador de plataforma"))::guardarEntidad, TipoRol.ADMINISTRADOR_PLATAFORMA, TipoRol.ORGANISMO_DE_CONTROL, TipoRol.ENTIDAD_PRESTADORA);
      app.post("admin/establecimiento", ((CargaManualController) FactoryController.controller("Administrador de plataforma"))::guardarEstablecimiento, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/servicio", ((CargaManualController) FactoryController.controller("Administrador de plataforma"))::guardarServicio, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/prestacion", ((CargaManualController) FactoryController.controller("Administrador de plataforma"))::guardarPrestacion, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.get("admin/cargaMasiva", ((CargaMasivaController) FactoryController.controller("Carga masiva"))::show, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/cargaMasiva", ((CargaMasivaController) FactoryController.controller("Carga masiva"))::cargaMasiva);

      app.get("admin/usuarios", ((AuthController) FactoryController.controller("Auth"))::showAdmin, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/usuarios/entidad", ((AuthController) FactoryController.controller("Auth"))::registerEntidad, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/usuarios/organismo", ((AuthController) FactoryController.controller("Auth"))::registerOrganismo, TipoRol.ADMINISTRADOR_PLATAFORMA);

      app.get("/", ((HomeController) FactoryController.controller("Home"))::index);
      app.get("admin", ((HomeController) FactoryController.controller("Home"))::showAdmin, TipoRol.ADMINISTRADOR_PLATAFORMA);

    });

    app.routes(() -> {
      app.get("organismoDeControl/", ((OrganismoDeControlController) FactoryController.controller("Organismo De Control"))::show, TipoRol.ORGANISMO_DE_CONTROL);
      app.get("organismoDeControl/ranking/{id}", ((InformesController) FactoryController.controller("Informe"))::rankingOrganismo, TipoRol.ORGANISMO_DE_CONTROL);
    });

    app.routes(() -> {
      app.get("entidadPrestadora/", ((EntidadPrestadoraController) FactoryController.controller("Entidad Prestadora"))::show, TipoRol.ENTIDAD_PRESTADORA);
      app.get("entidadPrestadora/ranking/{id}", ((InformesController) FactoryController.controller("Informe"))::rankingPrestadora, TipoRol.ENTIDAD_PRESTADORA);

    });

    app.routes(() -> {
      app.get("perfil", ((PerfilController) FactoryController.controller("Perfil"))::index);
      app.post("perfil", ((PerfilController) FactoryController.controller("Perfil"))::save);
    });

    app.routes(() -> {
      app.get("intereses", ((InteresesController) FactoryController.controller("Intereses"))::index);
      app.post("intereses", ((InteresesController) FactoryController.controller("Intereses"))::save);
      app.delete("intereses/{id}", ((InteresesController) FactoryController.controller("Intereses"))::delete);
    });

    app.routes(() -> {
      app.get("admin/config", ((ConfigController) FactoryController.controller("Config"))::show, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/config", ((ConfigController) FactoryController.controller("Config"))::save, TipoRol.ADMINISTRADOR_PLATAFORMA);
    });

    app.exception(CredencialesInvalidaException.class, ExceptionHandler::handleInvalidCredentials);
    app.exception(PermisosInvalidosException.class, ExceptionHandler::handleInvalidPermission);
    app.exception(EntidadNoExistenteException.class, ExceptionHandler::handleEntidadNoExistente);
    app.exception(PaginaNoEncontradaException.class, ExceptionHandler::handlePaginaNoEncontrada);
    app.exception(ServerErrorException.class, ExceptionHandler::handleServerException);

    app.after(ctx -> {
      entityManager().clear();
    });
  }
}


