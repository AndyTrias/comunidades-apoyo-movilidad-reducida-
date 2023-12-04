package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.factories.FactoryController;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.configs.Config;
import models.entidades.Entidad;
import models.entidades.EntidadPrestadora;
import models.entidades.OrganismoDeControl;
import models.external.json.ServicioJson;
import models.external.retrofit.apiServicio3.ApiServicio3;
import models.external.retrofit.apiServicio3.responseClases.EntidadDTO;
import models.external.retrofit.apiServicio3.responseClases.PayloadServicio3DTO;
import models.rankings.criterios.CriteriosDeEntidades;
import models.rankings.criterios.MayorCantidad;
import models.rankings.criterios.MayorImpacto;
import models.rankings.criterios.MayorTiempo;
import models.rankings.estrategiaDeExportacion.EstrategiaDeExportacion;
import models.rankings.estrategiaDeExportacion.ExportarAJson;
import models.rankings.informes.Exportador;
import models.rankings.informes.GeneradorDeInformes;
import models.rankings.informes.Informe;
import models.rankings.informes.Ranking;
import models.repositorios.*;
import org.jvnet.staxex.StAxSOAPBody;
import server.exceptions.EntidadNoExistenteException;
import server.utils.Mapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


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
    Informe inf = buscarInforme(Long.valueOf(ctx.pathParam("id")));
    String rutaInforme = inf.getPath();

    List<Ranking> ranking = new ServicioJson().importarDesdeJson(rutaInforme);
    ranking = ranking.stream().filter(r -> entidadesDisponibles.contains(r.entidad())).collect(Collectors.toList());

    model.put("criterio", inf.getNombre());
    model.put("ranking", ranking);
    ctx.render("rankings/ranking.hbs", model);
  }

  public Informe buscarInforme(Long id) {
    return Optional.ofNullable(repoInformes.buscar(id))
        .orElseThrow(() -> new EntidadNoExistenteException("No existe el informe"));

  }

  public void generarRankings() {
    List<CriteriosDeEntidades> criterios = Arrays.asList(
        new MayorTiempo("Minutos de resolucion"),
        new MayorCantidad("Cantidad de incidentes"),
        new MayorImpacto("Mayor grado de impacto")
    );

    List<Entidad> entidades = repoEntidad.buscarTodos();
    EstrategiaDeExportacion estrategia = new ExportarAJson(new ServicioJson());
    GeneradorDeInformes generadorDeInformes = new GeneradorDeInformes();

    for (CriteriosDeEntidades criterio : criterios) {

      String nombreArchivo = crearNombreArchivo(criterio);
      Exportador exportador = new Exportador(generadorDeInformes, estrategia);
      exportador.exportarConEstrategia(entidades, criterio, nombreArchivo);

      Informe informe = new Informe(new Date(), nombreArchivo, criterio.getNombre());
      repoInformes.agregar(informe);
    }

  }

  public String crearNombreArchivo(CriteriosDeEntidades criterio) {
    LocalDateTime currentDateTime = LocalDateTime.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    String formattedDateTime = currentDateTime.format(formatter);

    return Config.getInstance().PATH_INFORMES + criterio.getNombreInterno() +
        "_" + formattedDateTime + ".json";
  }

}
