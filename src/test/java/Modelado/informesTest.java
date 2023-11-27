package Modelado;

import models.configs.Config;
import models.external.json.ServicioJson;
import models.rankings.estrategiaDeExportacion.EstrategiaDeExportacion;
import models.rankings.estrategiaDeExportacion.ExportarAJson;
import models.rankings.estrategiaDeExportacion.ExportarCSV;
import models.rankings.informes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import models.rankings.criterios.MayorCantidad;
import models.rankings.criterios.MayorTiempo;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoEntidad;

import java.time.LocalDate;
import java.util.List;


public class informesTest {
  private RepoComunidad repoComunidad;
  private RepoEntidad repoEntidad;
  private GeneradorDeInformes generadorDeInformes;
  private  List<List<String>> informes;

  @BeforeEach
    public void setUp(){
      repoComunidad = new RepoComunidad();
      repoEntidad = new RepoEntidad();


      generadorDeInformes = new GeneradorDeInformes();
      generadorDeInformes.agregarCriterioDeEntidad(new MayorCantidad("Cantidad de incidentes"));
      generadorDeInformes.agregarCriterioDeEntidad(new MayorTiempo("Tiempo de resolucion"));

      informes = generadorDeInformes.generarDatos(RepoEntidad.INSTANCE.buscarTodos());
    }

    @Test
    public void soyUnTest(){
      ExportarCSV exportarCSVMock = Mockito.mock(ExportarCSV.class);

      Exportador exportador = new Exportador(generadorDeInformes, exportarCSVMock);
      exportador.exportarConEstrategia(RepoEntidad.INSTANCE.buscarTodos(), "informes.csv");

      Mockito.verify(exportarCSVMock).exportar(informes, "informes.csv");
    }

    @Test
    public void soyOtroTest() {

      ServicioJson servicioJsonMock = Mockito.mock(ServicioJson.class);
      EstrategiaDeExportacion estrategia = new ExportarAJson(servicioJsonMock);

      Exportador exportador = new Exportador(generadorDeInformes, estrategia);
      exportador.exportarConEstrategia(repoEntidad.buscarTodos(), "ranking_" + LocalDate.now() + ".json");

      String ruta = "ranking_" + LocalDate.now() + ".json";

      Mockito.verify(servicioJsonMock).exportarAJson(informes, ruta);
    }

    @Test
    public void csvSinMock(){
      EstrategiaDeExportacion estrategia = new ExportarCSV();
      Exportador exportador = new Exportador(generadorDeInformes, estrategia);
      exportador.exportarConEstrategia(repoEntidad.buscarTodos(), "informes.csv");
    }

    @Test
    public void jsonSinMock() {
      EstrategiaDeExportacion estrategiaDeExportacion = new ExportarAJson(new ServicioJson());
      Exportador exportador = new Exportador(generadorDeInformes, estrategiaDeExportacion);
      exportador.exportarConEstrategia(repoEntidad.buscarTodos(),  "informes.json");
    }

  }
