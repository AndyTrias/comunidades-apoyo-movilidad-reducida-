package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.comunidades.Comunidad;
import models.comunidades.Membresia;
import models.comunidades.TipoRol;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoPrestacion;
import models.repositorios.RepoRol;
import models.servicios.PrestacionDeServicio;
import models.usuario.Usuario;


import java.util.*;

@AllArgsConstructor
public class ComunidadController extends BaseController {


  private RepoComunidad repoComunidad;
  private RepoPrestacion repoPrestacion;


  public void index(Context ctx) {
    Usuario usuario = usuarioLogueado(ctx);
    List<Comunidad> comunidades = usuario.getComunidades();
    Map<String, Object> model = new HashMap<>();
    model.put("comunidades", comunidades);

    List<Comunidad> comunidadesNoPertenecientes = repoComunidad.buscarTodos().stream().
        filter(comunidad -> !usuario.getComunidades().contains(comunidad)).
        toList();

    model.put("comunidadesQNoPretUsu", comunidadesNoPertenecientes);
    ctx.render("comunidades/comunidades.hbs", model);
  }


  public void delete(Context ctx) {
    Comunidad comunidad = obtenerComunidad(ctx);
    if (comunidad == null) {
      return;
    }

    repoComunidad.eliminar(comunidad);
    ctx.status(204);
  }


  public void create(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    List<PrestacionDeServicio> servicios = repoPrestacion.buscarTodos();
    model.put("servicios", servicios);
    ctx.render("comunidades/crearComunidad.hbs", model);
  }

  public void save(Context ctx) {

    Usuario usuario = usuarioLogueado(ctx);
    Comunidad comunidad = crearComunidadConAdmin(usuario, ctx.formParam("nombre"));

    List<String> servicioIds = ctx.formParams("prestaciones");

    agregarServiciosDeInteresAComunidad(servicioIds, comunidad);

    repoComunidad.agregar(comunidad);

    ctx.redirect("/comunidades");
  }


  public void agregarPrestacion(Context ctx) {
    Comunidad comunidad = obtenerComunidad(ctx);
    if (comunidad == null) {
      return;
    }

    PrestacionDeServicio prestacion = repoPrestacion.buscar(Long.parseLong(ctx.formParam("prestacion")));
    if (prestacion == null) {
      ctx.status(404);
      ctx.result("Prestacion no encontrada");
      return;
    }

    Usuario usuario = usuarioLogueado(ctx);
    if (usuario == null ||
        !usuario.getMembresia(comunidad).getRol().
            tenesPermiso("agregar_servicio_de_interes")) {
      ctx.status(401);
      return;
    }


    comunidad.agregarServicioDeInteres(prestacion);
    repoComunidad.modificar(comunidad);
    ctx.redirect("/comunidades/" + comunidad.getId() + "/incidentes");
  }

  public void unir(Context ctx) {
    Usuario usuario = usuarioLogueado(ctx);
    Comunidad comunidad = obtenerComunidad(ctx);

    if (comunidad == null) {
      return;
    }

    Membresia membresia = new Membresia(comunidad, usuario, new RepoRol().buscarPorNombre(TipoRol.MIEMBRO));
    comunidad.agregarMembresia(membresia);
    usuario.unirseAComunidad(membresia);
    repoComunidad.modificar(comunidad);
    ctx.redirect("/comunidades");
  }

  public void show(Context ctx) {
    Comunidad comunidad = obtenerComunidad(ctx);
    if (comunidad == null) {
      return;
    }

    Usuario usuario = usuarioLogueado(ctx);
    List<PrestacionDeServicio> posiblesPrestacionesNuevas = obtenerPrestacionesNuevas(comunidad);

    Map<String, Object> model = new HashMap<>();
    model.put("incidentes", comunidad.getIncidentes());
    model.put("comunidad", comunidad);
    model.put("membresia", usuario.getMembresia(comunidad));
    model.put("estadisticas", comunidad.getEstadisticas());
    model.put("prestacionesNoPertenecenAComunidad", posiblesPrestacionesNuevas);

    ctx.render("comunidades/comunidad.hbs", model);
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

  private Comunidad crearComunidadConAdmin(Usuario usuario, String nombre) {
    Comunidad comunidad = new Comunidad(nombre);
    Membresia membresia = new Membresia(comunidad, usuario, new RepoRol().buscarPorNombre(TipoRol.ADMINISTRADOR_COMUNIDAD));
    comunidad.agregarMembresia(membresia);
    usuario.unirseAComunidad(membresia);
    return comunidad;
  }


  private List<PrestacionDeServicio> obtenerPrestacionesNuevas(Comunidad comunidad) {
    return repoPrestacion.buscarTodos().stream()
        .filter(p -> !comunidad.getServiciosDeInteres().contains(p))
        .toList();
  }

  private void agregarServiciosDeInteresAComunidad(List<String> servicioIds, Comunidad comunidad) {
    List<Long> idsLong = servicioIds.stream()
        .map(Long::parseLong)
        .toList();

    for (Long id : idsLong) {
      PrestacionDeServicio prestacionDeServicio = repoPrestacion.buscar(id);
      if (prestacionDeServicio != null) {
        comunidad.agregarServicioDeInteres(prestacionDeServicio);
      }
    }
  }

}
