package controllers;

import com.sun.xml.bind.v2.TODO;
import io.javalin.http.Context;
import models.comunidades.Comunidad;
import models.incidentes.Incidente;
import models.incidentes.IncidenteDeComunidad;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoIncidentes;
import models.repositorios.RepoPrestacion;
import models.repositorios.RepoUsuario;
import models.usuario.Usuario;
import models.servicios.PrestacionDeServicio;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IncidenteDeComunidadController{
  private RepoComunidad repoComunidad;
  private RepoPrestacion repoPrestacion;

  public IncidenteDeComunidadController(RepoComunidad repoComunidad, RepoPrestacion repoPrestacion){
    this.repoComunidad = repoComunidad;
    this.repoPrestacion = repoPrestacion;
  }

  public void index(Context ctx) {
    Comunidad comunidad = obtenerComunidad(ctx);
    if (comunidad == null) {
      throw new RuntimeException("Comunidad no encontrada");
    }

    List<IncidenteDeComunidad> incidentes = comunidad.getIncidentes();
    Map<String, Object> estadisticasComunidad = comunidad.getEstadisticas();
    Map<String, Object> model = new HashMap<>();
    model.put("incidentes", incidentes);
    model.put("comunidad", comunidad);
    model.put("estadisticas", estadisticasComunidad);
    ctx.render("comunidades/listadoIncidentes.hbs", model);
  }

  public void show(Context ctx) {
    Comunidad comunidad = obtenerComunidad(ctx);
    if (comunidad == null) {
      throw new RuntimeException("Comunidad no encontrada");
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

    Long prestacionId = Long.valueOf(Objects.requireNonNull(ctx.formParam("prestacion_id")));
    PrestacionDeServicio prestacion = repoPrestacion.buscar(prestacionId);

    if (prestacion == null) {
      ctx.status(400);
      ctx.result("Prestacion no encontrada");
      return;
    }

    Incidente incidente = createIncidente(prestacion, ctx.formParam("observaciones"));

    comunidad.abrirIncidente(incidente);
    //TODO: Abrir incidente en todas las comunidades del usuario
    repoComunidad.modificar(comunidad);

    ctx.status(200);
    ctx.result("Incidente creado");
  }

  private Incidente createIncidente(PrestacionDeServicio prestacion, String observaciones) {
    RepoUsuario repoUsuario = new RepoUsuario();
    Usuario usuario = repoUsuario.buscar(3L);
    return new Incidente(usuario, observaciones, prestacion);
  }

  public void delete(Context ctx) {
    Comunidad comunidad = obtenerComunidad(ctx);
    if (comunidad == null) {
      return;
    }

    Long incidenteId = Long.parseLong(ctx.pathParam("id_incidente"));
    IncidenteDeComunidad incidente = repoComunidad.buscarIncidenteDeComunidad(comunidad.getId(), incidenteId);

    if (incidente == null) {
      ctx.status(404);
      ctx.result("Incidente no encontrado");
      return;
    }

    comunidad.cerrarIncidente(incidente.getIncidente(), new Usuario());
    incidente.getIncidente().cerrar();
    //      andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidente));
    //TODO: Cerrar incidente en todas las comunidades del usuario
    repoComunidad.modificar(comunidad);
    ctx.status(200);
    ctx.redirect("/comunidades/" + comunidad.getId() + "/incidentes" + incidente.getId());
  }



  public void create(Context ctx) {
    Comunidad comunidad = obtenerComunidad(ctx);
    if (comunidad == null) {
      return;
    }

    Map<String, Object> model = new HashMap<>();
    model.put("comunidad", comunidad);

    //    ctx.render("incidentes/incidente.hbs", model);
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

  private Comunidad obtenerComunidad(Context ctx) {
    Long comunidad_id = Long.parseLong(ctx.pathParam("id"));
    Comunidad comunidad = repoComunidad.buscar(comunidad_id);

    if (comunidad == null) {
      ctx.status(404);
      ctx.result("Comunidad no encontrada");
    }

    return comunidad;
  }

}


//comunidades/id/incidentes -> Listado de incidentes (GET) INDEX
//comunidades/id/incidentes/create -> CREATE
//comunidades/id/incidentes/id -> Detalle de incidente(Te dice si esta abierto o no) (GET) SHOW
//comunidades/id/incidentes/id -> Cerrar incidente (POST) EDIT
//comunidades/id/incidentes -> Crea un incidente (POST) SAVE