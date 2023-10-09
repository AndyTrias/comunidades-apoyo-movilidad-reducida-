package server;

import controllers.*;
import controllers.factories.FactoryController;
import io.javalin.Javalin;
import models.comunidades.TipoRol;
import models.entidades.Establecimiento;
import models.repositorios.RepoComunidad;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Router {

  public static void init() {
    Javalin app = Server.app();

    app.error(404, ctx -> {
      ctx.render("error.hbs");
    });

    app.get("/", ctx -> {
      ctx.render("home.hbs");
    });

    app.routes(() -> {

      app.get("comunidades", ((ComunidadController) FactoryController.controller("Comunidad"))::index);
      app.get("comunidades/create", ((ComunidadController) FactoryController.controller("Comunidad"))::create);
      //app.post("comunidades", ((ComunidadController) FactoryController.controller("Comunidad"))::save);
      path("comunidades/{id}/incidentes", () -> {
        app.get("comunidades/{id}/incidentes", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::index);
        app.get("comunidades/{id}/incidentes/create", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::create);
        app.get("comunidades/{id}/incidentes/{id_incidente}", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::show);
        app.post("comunidades/{id}/incidentes", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::save);
        app.post("comunidades/{id}/incidentes/{id_incidente}", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::delete);

      });
    });

    app.routes(() -> {
      app.get("login", ((AuthController) FactoryController.controller("Auth"))::showLogin);
      app.post("login", ((AuthController) FactoryController.controller("Auth"))::login);
      app.get("logout", ((AuthController) FactoryController.controller("Auth"))::logout);
    });

    app.routes(() -> {
      app.get("cargaMasiva", ((CargaMasivaController) FactoryController.controller("Carga masiva"))::show);
      app.post("cargaMasiva", ((CargaMasivaController) FactoryController.controller("Carga masiva"))::cargaMasiva);
    });

    app.routes(() -> {
      app.get("revisionDeIncidentes", ((RevisionDeIncidenteController) FactoryController.controller("Revision de incidentes"))::show);
    });

    app.routes(() -> {
      app.post("sugerenciaDeFusion", ((ApiServicioController) FactoryController.controller("Sugerencia de fusion"))::fusionDeComunidades);
    });

    app.routes(() -> {
      app.get("admin", ((AdminController) FactoryController.controller("Administrador de plataforma"))::show);
      app.post("admin/entidad", ((AdminController) FactoryController.controller("Administrador de plataforma"))::guardarEntidad);
      app.post("admin/servicio", ((AdminController) FactoryController.controller("Administrador de plataforma"))::guardarServicio);
      app.post("admin/establecimientos", ((EstablecimientoController) FactoryController.controller("Establecimiento"))::save);
      app.get("admin/establecimientos", ((EstablecimientoController) FactoryController.controller("Establecimiento"))::show);
    });
  }
}


//comunidades/id/incidentes -> Listado de incidentes (GET) INDEX
//comunidades/id/incidentes/create -> CREATE
//comunidades/id/incidentes/id -> Detalle de incidente (GET) SHOW
//comunidades/id/incidentes/id -> Cerrar incidente (POST) EDIT
//comunidades/id/incidentes -> Crea un incidente (POST) CREATE
