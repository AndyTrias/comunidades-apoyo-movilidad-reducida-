package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.comunidades.Comunidad;
import models.comunidades.Membresia;
import models.comunidades.TipoRol;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoPrestacion;
import models.repositorios.RepoRol;
import models.repositorios.RepoUsuario;
import models.servicios.PrestacionDeServicio;
import models.usuario.Usuario;
import server.exceptions.EntidadNoExistenteException;
import server.exceptions.PermisosInvalidosException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class ComunidadController extends BaseController {

  private RepoComunidad repoComunidad;
  private RepoPrestacion repoPrestacion;
  private RepoUsuario repoUsuario;


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
    Comunidad comunidad = obtenerComunidad(ctx.pathParam("id"));
    PrestacionDeServicio prestacion = repoPrestacion.buscar(Long.parseLong(Objects.requireNonNull(ctx.formParam("prestacion"))));
    if (prestacion == null) {
      throw new EntidadNoExistenteException("No existe la prestacion");
    }

    Usuario usuario = usuarioLogueado(ctx);
    if (usuario == null || !usuario.getMembresia(comunidad).getRol().tenesPermiso("agregar_servicio_de_interes")) {
      throw new PermisosInvalidosException("No tienes los permisos para agregar un servicio");
    }


    comunidad.agregarServicioDeInteres(prestacion);
    repoComunidad.modificar(comunidad);
    ctx.redirect("/comunidades/" + comunidad.getId() + "/incidentes");
  }

  public void unir(Context ctx) {
    Usuario usuario = usuarioLogueado(ctx);
    Comunidad comunidad = obtenerComunidad(ctx.formParam("comunidad_id"));


    Membresia membresia = new Membresia(comunidad, usuario, new RepoRol().buscarPorNombre(TipoRol.MIEMBRO));
    comunidad.agregarMembresia(membresia);
    usuario.unirseAComunidad(membresia);
    repoUsuario.modificar(usuario);
    repoComunidad.modificar(comunidad);
    ctx.redirect("/comunidades");
  }

  public void show(Context ctx) {
    Comunidad comunidad = obtenerComunidad(ctx.pathParam("id"));

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
