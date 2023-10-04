package server;


//import controllers.IncidenteController;
import controllers.AuthController;
import controllers.ComunidadController;
import controllers.IncidenteDeComunidadController;
import controllers.factories.FactoryController;
import io.javalin.Javalin;
import models.comunidades.TipoRol;
import models.repositorios.RepoComunidad;

import static io.javalin.apibuilder.ApiBuilder.path;


public class Router {

  public static void init() {
    Javalin app = Server.app();

    app.error(404, ctx -> {
      ctx.render("error.hbs");
    });

    app.routes(() -> {
      app.get("comunidades", ((ComunidadController) FactoryController.controller("Comunidad"))::index, TipoRol.MIEMBRO);

      path("comunidades/{id}/incidentes", () -> {
        app.get("comunidades/{id}/incidentes", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::index, TipoRol.MIEMBRO);
        app.get("comunidades/{id}/incidentes/create", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::create, TipoRol.MIEMBRO);
        app.get("comunidades/{id}/incidentes/{id_incidente}", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::show, TipoRol.MIEMBRO);
        app.post("comunidades/{id}/incidentes", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::save, TipoRol.MIEMBRO);
        app.post("comunidades/{id}/incidentes/{id_incidente}", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::delete, TipoRol.MIEMBRO);
      });
    });

    app.routes(() -> {
      app.get("login", ((AuthController) FactoryController.controller("Auth"))::showLogin);
      app.post("login", ((AuthController) FactoryController.controller("Auth"))::login);
    });
  }
}


//comunidades/id/incidentes -> Listado de incidentes (GET) INDEX
//comunidades/id/incidentes/create -> CREATE
//comunidades/id/incidentes/id -> Detalle de incidente (GET) SHOW
//comunidades/id/incidentes/id -> Cerrar incidente (POST) EDIT
//comunidades/id/incidentes -> Crea un incidente (POST) CREATE
