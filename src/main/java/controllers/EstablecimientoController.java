package controllers;

import io.javalin.http.Context;
import models.entidades.Entidad;
import models.entidades.Establecimiento;
import models.localizacion.Localizacion;
import models.repositorios.RepoEntidad;
import models.repositorios.RepoEstablecimiento;
import models.repositorios.RepoPrestacion;
import models.repositorios.RepoServicio;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EstablecimientoController {
  private RepoEstablecimiento repoEstablecimiento;
  private RepoEntidad repoEntidad;
  private RepoServicio repoServicio;

  public EstablecimientoController(RepoEstablecimiento repoEstablecimiento, RepoEntidad repoEntidad, RepoServicio repoServicio) {
    this.repoEstablecimiento = repoEstablecimiento;
    this.repoEntidad = repoEntidad;
    this.repoServicio = repoServicio;
  }

  public void save(Context ctx) {
    System.out.println(ctx.formParamMap());
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
    ctx.redirect("/estableciemientos");

  }

  public void show(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    System.out.println(repoEstablecimiento.buscarTodos());
    model.put("establecimientos", repoEstablecimiento.buscarTodos());
    ctx.render("admin/establecimientos.hbs", model);
  }
}


