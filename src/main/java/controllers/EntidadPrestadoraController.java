package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.repositorios.RepoEntidadPrestadora;
import models.repositorios.RepoInformes;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class EntidadPrestadoraController extends BaseController {
  private RepoEntidadPrestadora repoEntidadPrestadora;
  private RepoInformes repoInformes;

  public void show(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    model.put("entidadPrestadora", true);
    model.put("prestadora", repoEntidadPrestadora.buscarporUsuarioDesignado(usuarioLogueado(ctx).getId()));
    model.put("rankings", repoInformes.buscarTodos());
    ctx.render("rankings/rankings.hbs", model);
  }
}