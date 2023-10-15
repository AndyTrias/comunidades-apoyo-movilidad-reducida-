package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.comunidades.Comunidad;
import models.dto.IncidenteDeComunidadDTO;
import models.incidentes.Incidente;
import models.incidentes.IncidenteDeComunidad;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoPrestacion;
import models.usuario.Usuario;
import models.servicios.PrestacionDeServicio;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@AllArgsConstructor
public class IncidenteDeComunidadController extends BaseController{
  private RepoComunidad repoComunidad;
  private RepoPrestacion repoPrestacion;

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

    PrestacionDeServicio prestacion = repoPrestacion.buscar(Long.parseLong(Objects.requireNonNull(ctx.formParam("prestacionId"))));
    Usuario usuario = usuarioLogueado(ctx);

    if (prestacion == null) {
      ctx.status(400);
      ctx.result("Prestacion no encontrada");
      return;
    }


    Incidente incidente = new Incidente(usuario, ctx.formParam("prestacionId"), prestacion, formatearFecha(ctx.formParam("fechaDeApertura")));
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

