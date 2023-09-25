package Modelado;

import comunidades.Comunidad;
import configs.Config;
import entidades.Entidad;
import external.json.ServicioJson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import rankings.criterios.ImpactoComunidades;
import rankings.criterios.MayorCantidad;
import rankings.criterios.MayorTiempo;
import rankings.informes.*;
import repositiorios.RepoComunidad;
import repositiorios.RepoEntidad;

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
      generadorDeInformes.agregarCriterioDeComunidad(new ImpactoComunidades("Impacto de la comunidad"));
      generadorDeInformes.agregarCriterioDeEntidad(new MayorCantidad("Cantidad de incidentes"));
      generadorDeInformes.agregarCriterioDeEntidad(new MayorTiempo("Tiempo de resolucion"));

      informes = generadorDeInformes.generarDatos(repoEntidad.buscarTodos(), repoComunidad.buscarTodos());
    }

    @Test
    public void soyUnTest(){
      ExportarCSV exportarCSVMock = Mockito.mock(ExportarCSV.class);

      Exportador exportador = new Exportador(generadorDeInformes, exportarCSVMock);
      exportador.exportarConEstrategia(repoEntidad.buscarTodos(), repoComunidad.buscarTodos(), "informes.csv");

      Mockito.verify(exportarCSVMock).exportar(informes, "informes.csv");
    }

    @Test
    public void soyOtroTest() {

      ServicioJson servicioJsonMock = Mockito.mock(ServicioJson.class);
      EstrategiaDeExportacion estrategia = new ExportarAJson(servicioJsonMock);

      Exportador exportador = new Exportador(generadorDeInformes, estrategia);
      exportador.exportarConEstrategia(repoEntidad.buscarTodos(), repoComunidad.buscarTodos(), "informes.json");

      String ruta = Config.PATH_INFORMES + "informes.json";

      Mockito.verify(servicioJsonMock).exportarAJson(informes, ruta);
    }

    @Test
    public void csvSinMock(){
      EstrategiaDeExportacion estrategia = new ExportarCSV();
      Exportador exportador = new Exportador(generadorDeInformes, estrategia);
      exportador.exportarConEstrategia(repoEntidad.buscarTodos(), repoComunidad.buscarTodos(), "informes.csv");
    }

    @Test
    public void jsonSinMock() {
      EstrategiaDeExportacion estrategiaDeExportacion = new ExportarAJson(new ServicioJson());
      Exportador exportador = new Exportador(generadorDeInformes, estrategiaDeExportacion);
      exportador.exportarConEstrategia(repoEntidad.buscarTodos(), repoComunidad.buscarTodos(), "informes.json");
    }

  }
