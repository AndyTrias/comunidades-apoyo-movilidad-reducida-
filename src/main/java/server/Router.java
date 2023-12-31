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
      app.get("admin/sugerenciaDeFusion", ((ApiServicioController) FactoryController.controller("Sugerencia de fusion"))::show);
      app.post("admin/sugerirComunidades", ((ApiServicioController) FactoryController.controller("Sugerencia de fusion"))::sugerirComunidades);
      app.post("admin/fusionarComunidades", ((ApiServicioController) FactoryController.controller("Sugerencia de fusion"))::fusionarComunidades);
      /*app.post("admin/sugerenciaDeFusion", ((ApiServicioController) FactoryController.controller("Sugerencia de fusion"))::fusionDeComunidades);*/
    });

    app.routes(() -> {
      app.get("admin/cargaManual", ((CargaManualController) FactoryController.controller("Administrador de plataforma"))::cargaManual, TipoRol.ADMINISTRADOR_PLATAFORMA);

      app.get("admin/entidades", ((EntidadController) FactoryController.controller("Entidad"))::show, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/entidades", ((CargaManualController) FactoryController.controller("Administrador de plataforma"))::guardarEntidad, TipoRol.ADMINISTRADOR_PLATAFORMA, TipoRol.ORGANISMO_DE_CONTROL, TipoRol.ENTIDAD_PRESTADORA);
      app.get("admin/entidades/{id}", ((EntidadController) FactoryController.controller("Entidad"))::edit, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/entidades/{id}", ((EntidadController) FactoryController.controller("Entidad"))::update, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/entidades/{id}/delete", ((EntidadController) FactoryController.controller("Entidad"))::delete, TipoRol.ADMINISTRADOR_PLATAFORMA);

      app.post("admin/establecimientos", ((CargaManualController) FactoryController.controller("Administrador de plataforma"))::guardarEstablecimiento, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.get("admin/entidades/{id}/establecimientos", ((EstablecimientoController) FactoryController.controller("Establecimiento"))::show, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.get("admin/establecimientos/{id}", ((EstablecimientoController) FactoryController.controller("Establecimiento"))::edit, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/establecimientos/{id}", ((EstablecimientoController) FactoryController.controller("Establecimiento"))::update, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/establecimientos/{id}/delete", ((EstablecimientoController) FactoryController.controller("Establecimiento"))::delete, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.get("admin/establecimientos", ((EstablecimientoController) FactoryController.controller("Establecimiento"))::index, TipoRol.ADMINISTRADOR_PLATAFORMA);

      app.get("admin/servicios", ((ServiciosController) FactoryController.controller("Servicio"))::show, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/servicios", ((CargaManualController) FactoryController.controller("Administrador de plataforma"))::guardarServicio, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.get("admin/servicios/{id}", ((ServiciosController) FactoryController.controller("Servicio"))::edit, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/servicios/{id}/delete", ((ServiciosController) FactoryController.controller("Servicio"))::delete, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/servicios/{id}", ((ServiciosController) FactoryController.controller("Servicio"))::update, TipoRol.ADMINISTRADOR_PLATAFORMA);

      app.post("admin/prestaciones", ((CargaManualController) FactoryController.controller("Administrador de plataforma"))::guardarPrestacion, TipoRol.ADMINISTRADOR_PLATAFORMA);

      app.get("admin/cargaMasiva", ((CargaMasivaController) FactoryController.controller("Carga masiva"))::show, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/cargaMasiva/entidades", ((CargaMasivaController) FactoryController.controller("Carga masiva"))::cargaEntidades, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/cargaMasiva/organismos", ((CargaMasivaController) FactoryController.controller("Carga masiva"))::cargaOrganismos, TipoRol.ADMINISTRADOR_PLATAFORMA);

      app.get("admin/usuarios", ((AuthController) FactoryController.controller("Auth"))::showAdmin, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/usuarios/entidad", ((AuthController) FactoryController.controller("Auth"))::registerEntidad, TipoRol.ADMINISTRADOR_PLATAFORMA);
      app.post("admin/usuarios/organismo", ((AuthController) FactoryController.controller("Auth"))::registerOrganismo, TipoRol.ADMINISTRADOR_PLATAFORMA);

      app.get("admin/comunidades", ((ComunidadController) FactoryController.controller("Comunidad"))::indexAdmin, TipoRol.ADMINISTRADOR_PLATAFORMA);

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


