package controllers;

import io.javalin.http.Context;

import models.comunidades.TipoRol;
import models.entidades.Entidad;
import models.entidades.Establecimiento;
import models.localizacion.Localizacion;
import models.localizacion.UbicacionExacta;
import models.repositorios.RepoEntidad;
import models.repositorios.RepoEstablecimiento;
import models.repositorios.RepoServicio;
import models.servicios.PrestacionDeServicio;
import models.servicios.Servicio;
import models.usuario.Usuario;
import server.exceptions.EntidadNoExistenteException;
import server.exceptions.PermisosInvalidosException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdminController extends BaseController {

  private RepoServicio repoServicio;
  private RepoEntidad repoEntidad;
  private RepoEstablecimiento repoEstablecimiento;

  public AdminController(RepoServicio repoServicio, RepoEntidad repoEntidad, RepoEstablecimiento repoEstablecimiento) {
    this.repoServicio = repoServicio;
    this.repoEntidad = repoEntidad;
    this.repoEstablecimiento = repoEstablecimiento;
  }

  public void show(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    List<Entidad> entidades = repoEntidad.buscarTodos();
    System.out.println(entidades);

    model.put("entidades", repoEntidad.buscarTodos());
    model.put("servicios", repoServicio.buscarTodos());
    model.put("establecimientos", repoEstablecimiento.buscarTodos());
    model.put("administrador", true);

    ctx.render("admin/admin.hbs", model);
  }

  public void guardarServicio(Context ctx) {
    Usuario usuario = usuarioLogueado(ctx);

    if (usuario == null || !usuario.getRol().tenesPermiso("crear_servicio")) {
      throw new PermisosInvalidosException("No tienes permisos para crear un servicio");
    }

    Servicio servicio = new Servicio(ctx.formParam("nombre"));
    repoServicio.agregar(servicio);
    ctx.redirect("/admin");
  }

  public void guardarEntidad(Context ctx) {

    Usuario usuario = usuarioLogueado(ctx);

    if (usuario == null || !usuario.getRol().tenesPermiso("crear_entidad")) {
      throw new PermisosInvalidosException("No tienes permisos para crear un entidad");
    }
    Entidad entidad = new Entidad(ctx.formParam("nombre"), new Localizacion());
    repoEntidad.agregar(entidad);
    ctx.redirect("/admin");
  }

  public void guardarEstablecimiento(Context ctx) {

    Usuario usuario = usuarioLogueado(ctx);

    if (usuario == null || !usuario.getRol().tenesPermiso("crear_establecimiento")) {
      throw new PermisosInvalidosException("No tienes permisos para crear un establecimiento");
    }

    Entidad entidad = repoEntidad.buscar(Long.valueOf(Objects.requireNonNull(ctx.formParam("entidad"))));
    if (entidad == null) {
      throw new EntidadNoExistenteException("No existe esa entidad");
    }

    Establecimiento establecimiento = new Establecimiento(
        ctx.formParam("nombre"),
        new Localizacion()
    );

    entidad.agregarEstablecimiento(establecimiento);
    repoEntidad.modificar(entidad);

    ctx.redirect("/admin");
  }


  public void guardarPrestacion(Context ctx) {

    Usuario usuario = usuarioLogueado(ctx);

    if (usuario == null || !usuario.getRol().tenesPermiso("crear_prestacion")) {
      throw new PermisosInvalidosException("No tienes permisos para crear un prestacion");
    }


    Establecimiento establecimiento = repoEstablecimiento.buscar(Long.valueOf(Objects.requireNonNull(ctx.formParam("establecimiento"))));
    if (establecimiento == null) {
      throw new EntidadNoExistenteException("No existe ese establecimiento");
    }

    Servicio servicio = repoServicio.buscar(Long.valueOf(Objects.requireNonNull(ctx.formParam("servicio"))));
    if (servicio == null) {
      throw new EntidadNoExistenteException("No existe ese servicio");
    }

    PrestacionDeServicio prestacionDeServicio = new PrestacionDeServicio(servicio, ctx.formParam("nombre"), new UbicacionExacta());
    establecimiento.agregarServicioPrestado(prestacionDeServicio);
    repoEstablecimiento.modificar(establecimiento);

    ctx.redirect("/admin");
  }
}
