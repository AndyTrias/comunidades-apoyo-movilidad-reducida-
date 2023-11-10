package controllers;


import lombok.AllArgsConstructor;
import models.configs.Config;
import models.entidades.Entidad;
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
import models.repositorios.RepoInformes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
public class InformesController extends BaseController {


  private RepoEntidad repoEntidad;
  private RepoInformes repoInformes;

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
