package server;

import com.twilio.rest.verify.v2.service.entity.Factor;
import controllers.IncidenteDeComunidadController;
import controllers.factories.FactoryController;
import io.javalin.Javalin;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoIncidentes;
import models.repositorios.RepoPrestacion;


public class Router {

  public static void init() {
    Javalin app = Server.app();

    app.get("/", ctx -> {
      ctx.sessionAttribute("item1", "Cosa 1");
      ctx.result("Hola mundo");
    });

    app.get("/saluda", ctx -> {
      ctx.result("Hola " + ctx.queryParam("nombre") + ", " + ctx.sessionAttribute("item1"));
    });

    app.get("/saludo-para/{nombre}", ctx -> ctx.result("Hola " + ctx.pathParam("nombre")));

    app.routes(() -> {
      app.get("comunidades/{id}/incidentes", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::index);
      app.get("comunidades/{id}/incidentes/{id_incidente}", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::show);
      app.post("comunidades/{id}/incidentes", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::save);
      //app.get("comunidades/{id}/incidentes/create", ((IncidenteDeComunidadController) FactoryController.controller("Incidente de comunidad"))::create)
    });
  }
}


//comunidades/id/incidentes -> Listado de incidentes (GET) INDEX
//comunidades/id/incidentes/create -> CREATE
//comunidades/id/incidentes/id -> Detalle de incidente (GET) SHOW
//comunidades/id/incidentes/id -> Cerrar incidente (POST) EDIT
//comunidades/id/incidentes -> Crea un incidente (POST) CREATE
