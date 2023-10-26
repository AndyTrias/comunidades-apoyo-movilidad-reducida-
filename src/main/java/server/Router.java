package server;


import controllers.*;
import controllers.factories.FactoryController;
import controllers.factories.HomeController;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import models.comunidades.TipoRol;
import server.exceptions.*;


public class Router {

  private static <T> T controller(String name) {
    return (T) FactoryController.controller(name);
  }

  public static void init() {
    Javalin app = Server.app();

    app.error(404, ctx -> {
      throw new PaginaNoEncontradaException("No se encontro la pagina solicitada");
    });

    app.error(500, ctx -> {
      throw new ServerErrorException("F");
    });

    app.get("/", ctx -> {
      HomeController homeController = controller("Home");
      homeController.index(ctx);
    });


    app.routes(() -> {
      ApiBuilder.path("comunidades/", () -> {
        ComunidadController comunidadController = controller("Comunidad");
        ApiBuilder.get(comunidadController::index);
        ApiBuilder.get("create", comunidadController::create);
        ApiBuilder.post(comunidadController::save);
        ApiBuilder.post("unirse", comunidadController::unir);
        ApiBuilder.path("{id}", () -> {
          ApiBuilder.get(comunidadController::show);
          ApiBuilder.post("prestacion", comunidadController::agregarPrestacion);
          ApiBuilder.path("incidentes", () -> {
            IncidenteDeComunidadController incidenteController = controller("Incidente de comunidad");
            ApiBuilder.get("create", incidenteController::create);
            ApiBuilder.get("{id_incidente}", incidenteController::show);
            ApiBuilder.post(incidenteController::save);
            ApiBuilder.post("{id_incidente}/cerrar", incidenteController::cerrarIncidente);
          });
        });
      });

      ApiBuilder.path("", () -> {
        AuthController authController = controller("Auth");
        ApiBuilder.get("login", authController::showLogin);
        ApiBuilder.get("logout", authController::logout);
        ApiBuilder.get("register", authController::showRegister);
        ApiBuilder.post("login", authController::login);
        ApiBuilder.post("register", authController::register);
      });

      ApiBuilder.path("cargaMasiva/", () -> {
        CargaMasivaController cargaMasivaController = controller("Carga masiva");
        ApiBuilder.get(cargaMasivaController::show);
        ApiBuilder.post(cargaMasivaController::cargaMasiva);
      });

      ApiBuilder.path("revisionDeIncidentes/", () -> {
        RevisionDeIncidenteController revisionController = controller("Revision de incidentes");
        ApiBuilder.post(revisionController::postUbicacionExacta);
        ApiBuilder.get(revisionController::show);
        ApiBuilder.get("{id}", revisionController::showIncidente);
        ApiBuilder.post("{id}", revisionController::resolucionDeIncidente);
      });

      ApiBuilder.path("sugerenciaDeFusion/", () -> {
        ApiServicioController apiServicioController = controller("Sugerencia de fusion");
        ApiBuilder.post(apiServicioController::fusionDeComunidades);
        ApiBuilder.post("rankingEntidades", apiServicioController::rankingEntidades);
      });

      ApiBuilder.path("admin/", () -> {
        AdminController adminController = controller("Administrador de plataforma");
        ApiBuilder.get(adminController::show, TipoRol.ADMINISTRADOR_PLATAFORMA);
        ApiBuilder.post("entidad", adminController::guardarEntidad, TipoRol.ADMINISTRADOR_PLATAFORMA);
        ApiBuilder.post("establecimiento", adminController::guardarEstablecimiento, TipoRol.ADMINISTRADOR_PLATAFORMA);
        ApiBuilder.post("servicio", adminController::guardarServicio, TipoRol.ADMINISTRADOR_PLATAFORMA);
        ApiBuilder.post("prestacion", adminController::guardarPrestacion, TipoRol.ADMINISTRADOR_PLATAFORMA);
      });

      ApiBuilder.path("perfil/", () -> {
        PerfilController perfilController = controller("Perfil");
        ApiBuilder.get(perfilController::index);
        ApiBuilder.post(perfilController::save);
      });
    });

    app.exception(CredencialesInvalidaException.class, ExceptionHandler::handleInvalidCredentials);
    app.exception(PermisosInvalidosException.class, ExceptionHandler::handleInvalidPermission);
    app.exception(EntidadNoExistenteException.class, ExceptionHandler::handleEntidadNoExistente);
    app.exception(PaginaNoEncontradaException.class, ExceptionHandler::handlePaginaNoEncontrada);
    app.exception(ServerErrorException.class, ExceptionHandler::handleServerException);
  }
}


