package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.comunidades.Comunidad;
import models.incidentes.Incidente;
import models.incidentes.IncidenteDeComunidad;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoIncidentes;
import models.repositorios.RepoPrestacion;
import models.servicios.PrestacionDeServicio;
import models.usuario.Usuario;
import server.exceptions.EntidadNoExistenteException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@AllArgsConstructor
public class IncidenteDeComunidadController extends BaseController{
  private RepoComunidad repoComunidad;
  private RepoPrestacion repoPrestacion;
  private RepoIncidentes repoIncidente;


  public void show(Context ctx) {
    Map<String, Object> model = new HashMap<>();

    Comunidad comunidad = obtenerComunidadConUsuario(ctx, ctx.pathParam("id"));
    IncidenteDeComunidad incidente = obtenerIncidenteDeComunidad(comunidad, ctx.pathParam("id_incidente"));

    model.put("incidente", incidente);
    ctx.render("comunidades/cierreIncidente.hbs", model);

  }


  public void save(Context ctx) {
    Comunidad comunidad = obtenerComunidadConUsuario(ctx, ctx.pathParam("id"));
    Usuario usuario = usuarioLogueado(ctx);

    PrestacionDeServicio prestacion = repoPrestacion.buscar(Long.parseLong(Objects.requireNonNull(ctx.formParam("prestacionId"))));
    if (prestacion == null) {
      throw new EntidadNoExistenteException("No existe esa prestacion");
    }

    Incidente incidente = new Incidente(usuario, ctx.formParam("observaciones"), prestacion, new Date());
    repoIncidente.agregar(incidente);
    List<Comunidad> comunidades = usuario.getComunidades();
    comunidades.stream()
            .filter(c -> c.getServiciosDeInteres().contains(prestacion))
            .forEach(c -> {
              c.abrirIncidente(incidente);
              repoComunidad.modificar(c);
            });

    ctx.redirect("/comunidades/" + comunidad.getId());
  }

  public void cerrarIncidente(Context ctx) {
    Comunidad comunidad = obtenerComunidadConUsuario(ctx, ctx.pathParam("id"));
    Usuario usuario = usuarioLogueado(ctx);

    IncidenteDeComunidad incidente = obtenerIncidenteDeComunidad(comunidad, ctx.pathParam("id_incidente"));
    PrestacionDeServicio prestacion = incidente.getIncidente().getPrestacionDeServicio();
    usuario.getComunidades().stream()
            .filter(c -> c.getServiciosDeInteres().contains(prestacion))
            .forEach(c -> {
              c.cerrarIncidente(incidente.getIncidente(), usuario);
              repoComunidad.modificar(c);
            });

    ctx.redirect("/comunidades/" + comunidad.getId());

  }

  public void create(Context ctx) {
    Comunidad comunidad = obtenerComunidadConUsuario(ctx, ctx.pathParam("id"));

    Map<String, Object> model = new HashMap<>();
    model.put("comunidad", comunidad);
    ctx.render("comunidades/aperturaIncidente.hbs", model);
  }


  private Comunidad obtenerComunidadConUsuario(Context ctx, String id) {
    Comunidad comunidad = obtenerComunidad(id);

    if (!usuarioLogueado(ctx).getComunidades().contains(comunidad)) {
      throw new EntidadNoExistenteException("No perteneces a esa comunidad");
    }

    return comunidad;

  }

  private IncidenteDeComunidad obtenerIncidenteDeComunidad(Comunidad comunidad, String id) {
    Long incidenteIdLong = Long.parseLong(id);
    IncidenteDeComunidad incidente = repoComunidad.buscarIncidenteDeComunidad(comunidad.getId(), incidenteIdLong);
    if (incidente == null) {
      throw new EntidadNoExistenteException("No existe el incidente seleccionado");
    }
    return incidente;
  }
}

