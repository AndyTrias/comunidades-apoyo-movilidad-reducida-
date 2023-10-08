package controllers;

import io.javalin.http.Context;

import models.entidades.Entidad;
import models.localizacion.Localizacion;
import models.repositorios.RepoEntidad;
import models.repositorios.RepoServicio;
import models.repositorios.RepoPrestacion;
import models.servicios.Servicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminController {

  private RepoServicio repoServicio;
  private RepoEntidad repoEntidad;

  public AdminController(RepoServicio repoServicio, RepoEntidad repoEntidad) {
    this.repoServicio = repoServicio;
    this.repoEntidad = repoEntidad;
  }

  public void show(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    List<Entidad> entidades = repoEntidad.buscarTodos();
    System.out.println(entidades);

    model.put("entidades", repoEntidad.buscarTodos());

    ctx.render("admin/admin.hbs", model);
  }

  public void guardarServicio(Context ctx) {
    Servicio servicio = new Servicio(ctx.formParam("nombre"));
    repoServicio.agregar(servicio);
    ctx.redirect("/admin");
  }

  public void guardarEntidad(Context ctx) {
    Entidad entidad = new Entidad(ctx.formParam("nombre"), new Localizacion());
    repoEntidad.agregar(entidad);
    ctx.redirect("/admin");
  }


}
