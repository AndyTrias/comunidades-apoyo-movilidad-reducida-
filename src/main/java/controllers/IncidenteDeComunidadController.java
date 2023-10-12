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
import java.util.stream.Collectors;

public class IncidenteDeComunidadController extends BaseController{
  private RepoComunidad repoComunidad;
  private RepoPrestacion repoPrestacion;

  public IncidenteDeComunidadController(RepoComunidad repoComunidad, RepoPrestacion repoPrestacion){
    this.repoComunidad = repoComunidad;
    this.repoPrestacion = repoPrestacion;
  }

  public void index(Context ctx) {
    Comunidad comunidad = obtenerComunidad(ctx);
    Usuario usuario = usuarioLogueado(ctx);
    if (comunidad == null) {
      throw new RuntimeException("Comunidad no encontrada");
    }

    List<PrestacionDeServicio> posiblesPrestacionesNuevas = repoPrestacion.buscarTodos().stream().
        filter(p -> !comunidad.getServiciosDeInteres().contains(p))
        .toList();

    List<IncidenteDeComunidad> incidentes = comunidad.getIncidentes();
    Map<String, Object> estadisticasComunidad = comunidad.getEstadisticas();
    Map<String, Object> model = new HashMap<>();
    model.put("incidentes", incidentes);
    model.put("comunidad", comunidad);
    model.put("membresia", usuario.getMembresia(comunidad));
    model.put("estadisticas", estadisticasComunidad);
    model.put("PrestacionesNoPertenecenAComunidad", posiblesPrestacionesNuevas);
    ctx.render("comunidades/listadoIncidentes.hbs", model);
  }

  public void show(Context ctx) {
    Map<String, Object> model = new HashMap<>();

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
      model.put("incidente", incidente);
      ctx.render("comunidades/cierreIncidente.hbs", model);

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
    Usuario usuario = usuarioLogueado(ctx);

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
    usuario.getComunidades().stream()
            .filter(c -> c.getServiciosDeInteres().contains(prestacion))
            .forEach(c -> {
              c.abrirIncidente(incidente);
              repoComunidad.modificar(c);
            });

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

    Usuario usuario = usuarioLogueado(ctx);
    PrestacionDeServicio prestacion = incidente.getIncidente().getPrestacionDeServicio();
    usuario.getComunidades().stream()
            .filter(c -> c.getServiciosDeInteres().contains(prestacion))
            .forEach(c -> {
              c.cerrarIncidente(incidente.getIncidente(), usuario);
              repoComunidad.modificar(c);
            });
    ctx.status(200);
    ctx.redirect("/comunidades/" + comunidad.getId() + "/incidentes");

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

