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
import repositiorios.RepoComunidades;
import repositiorios.RepoEntidades;

import java.util.List;


public class informesTest {
  private List<Comunidad> comunidades;
  private List<Entidad> entidades;
  private GeneradorDeInformes generadorDeInformes;
  private  List<List<String>> informes;

  @BeforeAll
  public static void generarDatos(){
    /*DataRepositorios dataRepositorios = new DataRepositorios();
    dataRepositorios.generarDatos();*/
  }

  @BeforeEach
    public void setUp(){
      /*comunidades = RepoComunidades.getInstance().getComunidades();
      entidades = RepoEntidades.getInstance().getEntidades();*/

      generadorDeInformes = new GeneradorDeInformes();
      generadorDeInformes.agregarCriterioDeComunidad(new ImpactoComunidades("Impacto de la comunidad"));
      generadorDeInformes.agregarCriterioDeEntidad(new MayorCantidad("Cantidad de incidentes"));
      generadorDeInformes.agregarCriterioDeEntidad(new MayorTiempo("Tiempo de resolucion"));

      informes = generadorDeInformes.generarDatos(entidades, comunidades);
    }

    @Test
    public void soyUnTest(){
      ExportarCSV exportarCSVMock = Mockito.mock(ExportarCSV.class);

      Exportador exportador = new Exportador(generadorDeInformes, exportarCSVMock);
      exportador.exportarConEstrategia(entidades, comunidades, "informes.csv");

      Mockito.verify(exportarCSVMock).exportar(informes, "informes.csv");
    }

    @Test
    public void soyOtroTest() {

      ServicioJson servicioJsonMock = Mockito.mock(ServicioJson.class);
      EstrategiaDeExportacion estrategia = new ExportarAJson(servicioJsonMock);

      Exportador exportador = new Exportador(generadorDeInformes, estrategia);
      exportador.exportarConEstrategia(entidades, comunidades, "informes.json");

      String ruta = Config.PATH_INFORMES + "informes.json";

      Mockito.verify(servicioJsonMock).exportarAJson(informes, ruta);
    }

    @Test
    public void csvSinMock(){
      EstrategiaDeExportacion estrategia = new ExportarCSV();
      Exportador exportador = new Exportador(generadorDeInformes, estrategia);
      exportador.exportarConEstrategia(entidades, comunidades, "informes.csv");
    }

    @Test
    public void jsonSinMock() {
      EstrategiaDeExportacion estrategiaDeExportacion = new ExportarAJson(new ServicioJson());
      Exportador exportador = new Exportador(generadorDeInformes, estrategiaDeExportacion);
      exportador.exportarConEstrategia(entidades, comunidades, "informes.json");
    }

  }
