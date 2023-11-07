package controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.entidades.Entidad;
import models.entidades.OrganismoDeControl;
import models.rankings.informes.Informe;
import models.repositorios.RepoEntidadPrestadora;
import models.repositorios.RepoInformes;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    ctx.render("show/rankings.hbs", model);
  }
}


