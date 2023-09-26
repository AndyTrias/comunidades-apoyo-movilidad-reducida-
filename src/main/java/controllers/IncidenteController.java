//package controllers;
//
//import io.javalin.http.Context;
//import models.comunidades.Comunidad;
//import models.incidentes.Incidente;
//import models.repositiorios.RepoComunidad;
//import models.repositiorios.RepoIncidentes;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class IncidenteController {
//  private RepoIncidentes repoIncidentes;
//
//  public IncidenteController(RepoIncidentes repoIncidentes) {
//    this.repoIncidentes = repoIncidentes;
//  }
//
//  public void index(Context context){
//    Map<String, Object> model = new HashMap<>();
//
////    Necesitamos obtener el listado de incidentes de la comunidad
//    Integer id_comunidad = Integer.parseInt(context.pathParam("id"));
//
//    RepoIncidenteDeComunidad.buscarTodosLosIncidentes(id_comunidad);
//
//    Incidente Comunidad Estado FechaDeUltimaModificacion
//
////    Cuando esten los renders
////    Context.render("comunidades/incidente.html")
//
//  }
//
//  public void create(Context context){
//    Incidente incidente = new Incidente(context.usuario, context.observaciones, context.prestacion);
//    RepoIncidentes.guardar
//    List<Comunidad> comunidades = usuario.getComunidades();
//    comunidades.stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidente));
//
//    Incidente Comunidad Estado FechaDeUltimaModificacio
//    RepoIncidentesDeComunidad.guardarTodosLosIncidentes(incidente);
//
//  }
//}
//


//comunidades/id/incidentes -> Listado de incidentes (GET) INDEX
//comunidades/id/incidentes/estado/%estado% GET
//comunidades/id/incidentes/create -> CREATE
//comunidades/id/incidentes/id -> Detalle de incidente(Te dice si esta abierto o no) (GET) SHOW
//comunidades/id/incidentes/id -> Cerrar incidente (POST) EDIT
//comunidades/id/incidentes -> Crea un incidente (POST) CREATE