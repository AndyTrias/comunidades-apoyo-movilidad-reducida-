package controllers;

import io.javalin.http.Context;
import models.comunidades.Comunidad;
import models.dto.IncidenteDeComunidadDTO;
import models.incidentes.Incidente;
import models.incidentes.IncidenteDeComunidad;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoPrestacion;
import models.repositorios.RepoUsuario;
import models.usuario.Usuario;
import models.servicios.PrestacionDeServicio;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class IncidenteDeComunidadController{
  private RepoComunidad repoComunidad;
  private RepoPrestacion repoPrestacion;
  private RepoUsuario repoUsuario;

  public IncidenteDeComunidadController(RepoComunidad repoComunidad, RepoPrestacion repoPrestacion, RepoUsuario repoUsuario){
    this.repoComunidad = repoComunidad;
    this.repoPrestacion = repoPrestacion;
    this.repoUsuario = repoUsuario;
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
    IncidenteDeComunidadDTO incidenteDTO = new IncidenteDeComunidadDTO();
    incidenteDTO.setPrestacionId(Long.parseLong(Objects.requireNonNull(ctx.formParam("prestacionId"))));
    incidenteDTO.setObservaciones(ctx.formParam("observaciones"));
    incidenteDTO.setFechaDeApertura(formatearFecha(ctx.formParam("fechaDeApertura")));

    PrestacionDeServicio prestacion = repoPrestacion.buscar(incidenteDTO.getPrestacionId());
    Long usuarioId = Long.parseLong(Objects.requireNonNull(ctx.cookie("usuario_id")));
    Usuario usuario = repoUsuario.buscar(usuarioId);

    if (prestacion == null) {
      ctx.status(400);
      ctx.result("Prestacion no encontrada");
      return;
    } else if (usuario == null) {
      ctx.status(400);
      ctx.result("Usuario no encontrado");
      return;
    }

    Incidente incidente = new Incidente(usuario, incidenteDTO.getObservaciones(), prestacion, incidenteDTO.getFechaDeApertura());
    repoUsuario.buscarComunidadesConPrestacion(prestacion, usuarioId).forEach(c -> c.abrirIncidente(incidente));
    repoUsuario.modificar(usuario);

    ctx.status(200);
    ctx.redirect("/comunidades/" + comunidad.getId() + "/incidentes");
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

    ctx.render("comunidades/aperturaIncidente.hbs", model);
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

  private Date formatearFecha(String fecha) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    LocalDateTime fechaDeApertura = LocalDateTime.parse(fecha, formatter);
    Timestamp timestamp = Timestamp.valueOf(fechaDeApertura);
    return new Date(timestamp.getTime());
  }
}


//comunidades/id/incidentes -> Listado de incidentes (GET) INDEX
//comunidades/id/incidentes/create -> CREATE
//comunidades/id/incidentes/id -> Detalle de incidente(Te dice si esta abierto o no) (GET) SHOW
//comunidades/id/incidentes/id -> Cerrar incidente (POST) EDIT
//comunidades/id/incidentes -> Crea un incidente (POST) SAVE