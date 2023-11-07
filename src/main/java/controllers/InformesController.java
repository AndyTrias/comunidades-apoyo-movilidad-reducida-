package controllers;


import lombok.AllArgsConstructor;
import models.external.json.ServicioJson;
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
import java.util.Date;


@AllArgsConstructor
public class InformesController extends BaseController {


  private RepoEntidad repoEntidad;
  private RepoInformes repoInformes;

  public void generarRankings() {
    EstrategiaDeExportacion estrategia = new ExportarAJson(new ServicioJson());

    GeneradorDeInformes generadorDeInformes = new GeneradorDeInformes();
    generadorDeInformes.agregarCriterioDeEntidad(new MayorTiempo("Tiempo de resolucion"));
    generadorDeInformes.agregarCriterioDeEntidad(new MayorCantidad("Cantidad de incidentes"));
    String rutaArchivo = "ranking_" + LocalDate.now() + ".json";

    Exportador exportador = new Exportador(generadorDeInformes, estrategia);
    exportador.exportarConEstrategia(repoEntidad.buscarTodos(), rutaArchivo);


    Informe informe = new Informe(new Date(), rutaArchivo);
    repoInformes.agregar(informe);
  }

}
