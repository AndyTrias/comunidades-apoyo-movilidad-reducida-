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

    ctx.render("admin/admin.hbs", model);
  }

  public void guardarServicio(Context ctx) {
    Usuario usuario = usuarioLogueado(ctx);

    if (usuario == null || !usuario.getRol().tenesPermiso("crear_servicio")) {
      ctx.status(401);
      ctx.redirect("/admin");
    }

    Servicio servicio = new Servicio(ctx.formParam("nombre"));
    repoServicio.agregar(servicio);
    ctx.redirect("/admin");
  }

  public void guardarEntidad(Context ctx) {

    Usuario usuario = usuarioLogueado(ctx);

    if (usuario == null || !usuario.getRol().tenesPermiso("crear_entidad")) {
      ctx.status(401);
      ctx.redirect("/admin");
    }
    Entidad entidad = new Entidad(ctx.formParam("nombre"), new Localizacion());
    repoEntidad.agregar(entidad);
    ctx.redirect("/admin");
  }

  public void guardarEstablecimiento(Context ctx) {

    Usuario usuario = usuarioLogueado(ctx);

    if (usuario == null || !usuario.getRol().tenesPermiso("crear_establecimiento")) {
      ctx.status(401);
      ctx.redirect("/admin");
    }

    Entidad entidad = repoEntidad.buscar(Long.valueOf(Objects.requireNonNull(ctx.formParam("entidad"))));
    if (entidad == null) {
      ctx.redirect("/admin");
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
      ctx.status(401);
      ctx.redirect("/admin");
    }


    Establecimiento establecimiento = repoEstablecimiento.buscar(Long.valueOf(Objects.requireNonNull(ctx.formParam("establecimiento"))));
    if (establecimiento == null) {
      ctx.status(401);
      ctx.redirect("/admin");
    }

    Servicio servicio = repoServicio.buscar(Long.valueOf(Objects.requireNonNull(ctx.formParam("servicio"))));
    if (servicio == null) {
      ctx.redirect("/admin");
    }

    PrestacionDeServicio prestacionDeServicio = new PrestacionDeServicio(servicio, ctx.formParam("nombre"), new UbicacionExacta());
    establecimiento.agregarServicioPrestado(prestacionDeServicio);
    repoEstablecimiento.modificar(establecimiento);

    ctx.redirect("/admin");
  }
}
