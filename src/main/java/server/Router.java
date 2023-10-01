package server;

import controllers.IncidenteController;
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
  }
}



//    Server.app().routes(() -> {
//      get("servicios", ((ServiciosController) FactoryController.controller("Servicios"))::index);
//      get("servicios/crear", ((ServiciosController) FactoryController.controller("Servicios"))::create);
//      get("servicios/{id}", ((ServiciosController) FactoryController.controller("Servicios"))::show);
//      get("servicios/{id}/editar", ((ServiciosController) FactoryController.controller("Servicios"))::edit);
//      post("servicios/{id}", ((ServiciosController) FactoryController.controller("Servicios"))::update);
//      post("servicios", ((ServiciosController) FactoryController.controller("Servicios"))::save);
//      delete("servicios/{id}", ((ServiciosController) FactoryController.controller("Servicios"))::delete);
//
//      path("servicios/{id}/tareas", () -> {
//        get(((TareasController) FactoryController.controller("Tareas"))::index);
//        //TODO
//      });
//    });


//comunidades/id/incidentes -> Listado de incidentes (GET) INDEX
//comunidades/id/incidentes/estado/%estado% GET
//comunidades/id/incidentes/create -> CREATE
//comunidades/id/incidentes/id -> Detalle de incidente(Te dice si esta abierto o no) (GET) SHOW
//comunidades/id/incidentes/id -> Cerrar incidente (POST) EDIT
//comunidades/id/incidentes -> Crea un incidente (POST) CREATE
