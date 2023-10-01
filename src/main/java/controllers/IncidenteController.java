package controllers;

import io.javalin.http.Context;
import models.comunidades.Comunidad;
import models.incidentes.Incidente;
import models.incidentes.IncidenteDeComunidad;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoIncidenteDeComunidad;
import models.repositorios.RepoIncidentes;
import models.repositorios.RepoPrestacion;
import models.usuario.Usuario;
import models.servicios.PrestacionDeServicio;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IncidenteController {
  private RepoIncidentes repoIncidentes;
  private RepoComunidad repoComunidad;
  private RepoPrestacion repoPrestacion;


  public IncidenteController(RepoComunidad repoComunidad) {
    this.repoComunidad = repoComunidad;
  }

  public IncidenteController(RepoComunidad repoComunidad, RepoPrestacion repoPrestacion, RepoIncidentes repoIncidentes){
    this.repoComunidad = repoComunidad;
    this.repoPrestacion = repoPrestacion;
    this.repoIncidentes = repoIncidentes;
  }

  public void index(Context ctx) {
    Comunidad comunidad = obtenerComunidad(ctx);
    if (comunidad == null) {
      return; // The obtenerComunidad method has already set the response status and result
    }

    List<IncidenteDeComunidad> incidentes = comunidad.getIncidentes();
    ctx.json(incidentes); // Serialize and set the list of incidents as JSON response
  }

  public void show(Context ctx) {
    Comunidad comunidad = obtenerComunidad(ctx);
    if (comunidad == null) {
      return;
    }

    Long incidenteId = Long.parseLong(ctx.pathParam("id_incidente"));
    IncidenteDeComunidad incidente = repoComunidad.buscarIncidenteDeComunidad(comunidad.getId(), incidenteId);

    if (incidente == null) {
      ctx.status(404);
      ctx.result("Incidente no encontrado");
    } else {
      ctx.json(incidente);
    }
  }




  public void save(Context ctx) {
    Comunidad comunidad = obtenerComunidad(ctx);
    if (comunidad == null) {
      return;
    }

    Long prestacionId = parsePrestacionId(ctx.formParam("prestacion"));
    PrestacionDeServicio prestacion = findPrestacionById(prestacionId);

    if (prestacion == null) {
      ctx.status(400);
      ctx.result("Prestacion no encontrada");
      return;
    }

    Incidente incidente = createIncidente(prestacion, ctx.formParam("observaciones"));
    repoIncidentes.agregar(incidente);

    comunidad.abrirIncidente(incidente);
    repoComunidad.modificar(comunidad);

    ctx.status(200);
    ctx.json(incidente);
  }

  private Long parsePrestacionId(String prestacionParam) {
    try {
      return Long.valueOf(prestacionParam);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private PrestacionDeServicio findPrestacionById(Long prestacionId) {
    if (prestacionId == null) {
      return null; // Handle null or invalid prestacionId gracefully
    }
    return repoPrestacion.buscar(prestacionId);
  }

  private Incidente createIncidente(PrestacionDeServicio prestacion, String observaciones) {
    Usuario usuario = new Usuario(); // You might want to create a specific user
    return new Incidente(usuario, observaciones, prestacion);
  }





  private Comunidad obtenerComunidad(Context ctx) {
    Long comunidad_id = Long.parseLong(ctx.pathParam("id"));
    Comunidad comunidad = repoComunidad.buscar(comunidad_id);

    if (comunidad == null) {
      ctx.status(404);
      ctx.result("Comunidad no encontrada");
      return null;
    }

    return comunidad;
  }
}


//comunidades/id/incidentes -> Listado de incidentes (GET) INDEX
//comunidades/id/incidentes/create -> CREATE
//comunidades/id/incidentes/id -> Detalle de incidente(Te dice si esta abierto o no) (GET) SHOW
//comunidades/id/incidentes/id -> Cerrar incidente (POST) EDIT
//comunidades/id/incidentes -> Crea un incidente (POST) SAVE