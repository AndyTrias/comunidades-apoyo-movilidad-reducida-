package server;

import controllers.IncidenteController;
import controllers.comunidadController;
import io.javalin.Javalin;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoIncidentes;
import models.repositorios.RepoPrestacion;


public class Router {

  public static void init() {
    Javalin app = Server.app(); // Assuming Server.app() returns your Javalin instance

    app.get("/", ctx -> {
      ctx.sessionAttribute("item1", "Cosa 1");
      ctx.result("Hola mundo");
    });

    app.get("/saluda", ctx -> {
      ctx.result("Hola " + ctx.queryParam("nombre") + ", " + ctx.sessionAttribute("item1"));
    });

    app.get("/saludo-para/{nombre}", ctx -> ctx.result("Hola " + ctx.pathParam("nombre")));

    app.routes(() -> {
      app.get("comunidades/{id}/incidentes", new IncidenteController(new RepoComunidad())::index);
      app.get("comunidades/{id}/incidentes/{id_incidente}", new IncidenteController(new RepoComunidad())::show);
      app.post("comunidades/{id}/incidentes", new IncidenteController(new RepoComunidad(), new RepoPrestacion(), new RepoIncidentes())::save);

    });

    app.routes(()-> {
      // lista de comidades
      app.get("comunidades", new comunidadController(new RepoComunidad())::index);
      // obtener una comunidad definida
      app.get("comunidades/{id}", new IncidenteController(new RepoComunidad())::show);

    });
  }
}


//comunidades/id/incidentes -> Listado de incidentes (GET) INDEX
//comunidades/id/incidentes/create -> CREATE
//comunidades/id/incidentes/id -> Detalle de incidente (GET) SHOW
//comunidades/id/incidentes/id -> Cerrar incidente (POST) EDIT
//comunidades/id/incidentes -> Crea un incidente (POST) CREATE
