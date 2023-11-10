package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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


  public void index(Context ctx) throws IOException {
    Map<String, Object> model = new HashMap<>();
    OrganismoDeControl organismoDeControl = repoOrganismoDeControl.buscarPorUsarioDesignado(usuarioLogueado(ctx).getId());
    String rutaInforme = buscarRutaInforme(Long.valueOf(ctx.pathParam("id")));

    JsonNode nodo = parsearJson(rutaInforme);
    String criterio = nodo.get(0).get("Criterio").asText();
    List<Entidad> entidades = parsearRanking(nodo.get(0).get("Ranking"), organismoDeControl);

    model.put("criterio", criterio);
    model.put("entidades", entidades);
    ctx.render("show/ranking.hbs", model);

  }

  public String buscarRutaInforme(Long id) {
    return Optional.ofNullable(repoInformes.buscar(id))
        .orElseThrow(() -> new EntidadNoExistenteException("No existe el informe"))
        .getPath();
  }

  public JsonNode parsearJson(String rutaInforme) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readTree(new File(rutaInforme));
    } catch (FileNotFoundException e) {
      throw new EntidadNoExistenteException("No se pudo procesar el informe");
    }
  }

  private List<Entidad> parsearRanking(JsonNode rankingNode, OrganismoDeControl organismoDeControl) {
    List<Entidad> entidadesDelOrganismo = organismoDeControl.obtenerEntidades();
    List<Entidad> entidades = new ArrayList<>();
    String[] rankingArray = rankingNode.get(0).asText().split(",");

    Arrays.stream(rankingArray)
        .map(String::trim) // Trim leading and trailing whitespace
        .map(Long::parseLong)
        .forEach(id -> {
          Entidad entidad = repoEntidad.buscar(id);
          if (entidadesDelOrganismo.contains(entidad)) {
            entidades.add(entidad);
          }
        });

    return entidades;
  }
}
