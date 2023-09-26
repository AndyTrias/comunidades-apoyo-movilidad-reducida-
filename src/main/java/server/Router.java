package server;

public class Router {
  public static void init() {

    Server.app().get("/", ctx -> {
      ctx.result("Hello World");
    });
  }
}


//comunidades/id/incidentes -> Listado de incidentes (GET) INDEX
//comunidades/id/incidentes/create -> CREATE
//comunidades/id/incidentes/id -> Detalle de incidente (GET) SHOW
//comunidades/id/incidentes/id -> Eliminar incidente (POST) EDIT
//comunidades/id/incidentes -> Crea un incidente (POST) CREATE
