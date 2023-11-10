package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.configs.Config;
import models.entidades.Entidad;
import models.entidades.EntidadPrestadora;
import models.entidades.OrganismoDeControl;
import models.external.json.ServicioJson;
import models.rankings.criterios.CriteriosDeEntidades;
import models.rankings.criterios.MayorCantidad;
import models.rankings.criterios.MayorTiempo;
import models.rankings.estrategiaDeExportacion.EstrategiaDeExportacion;
import models.rankings.estrategiaDeExportacion.ExportarAJson;
import models.rankings.informes.Exportador;
import models.rankings.informes.GeneradorDeInformes;
import models.rankings.informes.Informe;
import models.repositorios.RepoEntidad;
import models.repositorios.RepoEntidadPrestadora;
import models.repositorios.RepoInformes;
import models.repositorios.RepoOrganismoDeControl;
import server.exceptions.EntidadNoExistenteException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;


@AllArgsConstructor
public class InformesController extends BaseController {

  private RepoEntidad repoEntidad;
  private RepoInformes repoInformes;
  private RepoEntidadPrestadora repoEntidadPrestadora;
  private RepoOrganismoDeControl repoOrganismoDeControl;

  public void rankingOrganismo(Context ctx) throws IOException {
    OrganismoDeControl organismoDeControl = repoOrganismoDeControl.buscarPorUsarioDesignado(usuarioLogueado(ctx).getId());
    renderizarRanking(ctx, organismoDeControl.obtenerEntidades());
  }

  public void rankingPrestadora(Context ctx) throws IOException {
    EntidadPrestadora prestadora = repoEntidadPrestadora.buscarporUsuarioDesignado(usuarioLogueado(ctx).getId());
    renderizarRanking(ctx, prestadora.getEntidades());
  }

  public void renderizarRanking(Context ctx, List<Entidad> entidadesDisponibles) throws IOException {
    Map<String, Object> model = new HashMap<>();
    String rutaInforme = buscarRutaInforme(Long.valueOf(ctx.pathParam("id")));

    JsonNode nodo = parsearJson(rutaInforme);
    String criterio = nodo.get(0).get("Criterio").asText();
    List<Entidad> entidades = parsearRanking(nodo.get(0).get("Ranking"), entidadesDisponibles);

    model.put("criterio", criterio);
    model.put("entidades", entidades);
    ctx.render("rankings/ranking.hbs", model);
  }

  public String buscarRutaInforme(Long id) {
    return Optional.ofNullable(repoInformes.buscar(id))
        .orElseThrow(() -> new EntidadNoExistenteException("No existe el informe"))
        .getPath();
  }

  private JsonNode parsearJson(String rutaInforme) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readTree(new File(rutaInforme));
    } catch (FileNotFoundException e) {
      throw new EntidadNoExistenteException("No se pudo procesar el informe");
    }
  }

  private List<Entidad> parsearRanking(JsonNode rankingNode, List<Entidad> entidadesDelOrganismo) {
    List<Entidad> entidades = new ArrayList<>();
    String[] rankingArray = rankingNode.get(0).asText().split(",");

    Arrays.stream(rankingArray)
        .map(String::trim)
        .map(Long::parseLong)
        .forEach(id -> {
          Entidad entidad = repoEntidad.buscar(id);
          if (entidadesDelOrganismo.contains(entidad)) {
            entidades.add(entidad);
          }
        });

    return entidades;
  }


  public void generarRankings() {
    List<CriteriosDeEntidades> criterios = Arrays.asList(
        new MayorTiempo("Tiempo de resolucion"),
        new MayorCantidad("Cantidad de incidentes")
    );

    List<Entidad> entidades = repoEntidad.buscarTodos();
    EstrategiaDeExportacion estrategia = new ExportarAJson(new ServicioJson());

    for (CriteriosDeEntidades criterio : criterios) {
      GeneradorDeInformes generadorDeInformes = new GeneradorDeInformes();
      generadorDeInformes.agregarCriterioDeEntidad(criterio);

      String nombreArchivo = Config.PATH_INFORMES + criterio.getNombreInterno() + "_" + LocalDate.now() + ".json";

      Exportador exportador = new Exportador(generadorDeInformes, estrategia);
      exportador.exportarConEstrategia(entidades, nombreArchivo);

      Informe informe = new Informe(new Date(), nombreArchivo, criterio.getNombre());
      repoInformes.agregar(informe);
    }
  }

}
