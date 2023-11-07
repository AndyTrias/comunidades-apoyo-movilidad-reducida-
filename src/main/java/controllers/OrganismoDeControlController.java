package controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.entidades.Entidad;
import models.entidades.OrganismoDeControl;
import models.repositorios.RepoEntidad;
import models.repositorios.RepoInformes;
import models.repositorios.RepoOrganismoDeControl;
import server.exceptions.EntidadNoExistenteException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

@AllArgsConstructor
public class OrganismoDeControlController extends BaseController {
  private RepoOrganismoDeControl repoOrganismoDeControl;
  private RepoInformes repoInformes;
  private RepoEntidad repoEntidad;


  public void show(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    model.put("organismoDeControl", true);
    model.put("organismo", repoOrganismoDeControl.buscarPorUsarioDesignado(usuarioLogueado(ctx).getId()));
    model.put("rankings", repoInformes.buscarTodos());
    ctx.render("show/rankings.hbs", model);
  }

  public void index(Context ctx) throws FileNotFoundException {
    OrganismoDeControl organismoDeControl = repoOrganismoDeControl.buscarPorUsarioDesignado(usuarioLogueado(ctx).getId());
    String rutaInforme = buscarRutaInforme(Long.valueOf(ctx.pathParam("id")));

    JsonArray jsonArray = parseJsonFile(rutaInforme);
    Map<String, List<Entidad>> mapCriterioEntidades = createCriterioEntidadesMap(jsonArray, organismoDeControl);

    Map<String, Object> model = new HashMap<>();
    model.put("mapCriterioEntidades", mapCriterioEntidades);
    ctx.render("show/ranking.hbs", model);
  }

  public String buscarRutaInforme(Long id) {
    return Optional.ofNullable(repoInformes.buscar(id))
        .orElseThrow(() -> new EntidadNoExistenteException("No existe el informe"))
        .getPath();
  }

  private JsonArray parseJsonFile(String filePath) throws FileNotFoundException {
    JsonParser parser = new JsonParser();
    JsonElement jsonElement = parser.parse(new FileReader(filePath));
    return jsonElement.getAsJsonArray();
  }

  private Map<String, List<Entidad>> createCriterioEntidadesMap(JsonArray jsonArray, OrganismoDeControl organismoDeControl) {
    Map<String, List<Entidad>> mapCriterioEntidades = new HashMap<>();

    for (JsonElement element : jsonArray) {
      JsonObject jsonObject = element.getAsJsonObject();
      String criterio = jsonObject.get("Criterio").getAsString();
      JsonArray rankingArray = jsonObject.get("Ranking").getAsJsonArray();

      List<Entidad> entidades = processRankingArray(rankingArray, organismoDeControl);
      mapCriterioEntidades.put(criterio, entidades);
    }
    return mapCriterioEntidades;
  }

  private List<Entidad> processRankingArray(JsonArray rankingArray, OrganismoDeControl organismoDeControl) {
    List<Entidad> entidades = new ArrayList<>();
    for (JsonElement rankElement : rankingArray) {
      String[] ids = rankElement.getAsString().split(",\\s*");
      for (String id : ids) {
        Long entityId = Long.parseLong(id);
        Optional<Entidad> optionalEntidad = Optional.ofNullable(repoEntidad.buscar(entityId));

        optionalEntidad.ifPresent(entidad -> {
          if (organismoDeControl.obtenerEntidades().contains(entidad)) {
            entidades.add(entidad);
          }
        });
      }
    }
    return entidades;
  }

}




