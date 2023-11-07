package Persistencia;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

public class RankingTest {
  private RepoInformes repoInformes;
  private RepoEntidad repoEntidad;

  @BeforeEach
  public void setUp() {
    repoInformes = new RepoInformes();
    repoEntidad = new RepoEntidad();
  }

  @Test
  public void TestRanking() {
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
