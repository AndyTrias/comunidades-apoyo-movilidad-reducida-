import comunidades.Comunidad;
import configs.Config;
import entidades.Entidad;
import external.json.ServicioJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import rankings.criterios.ImpactoComunidades;
import rankings.criterios.MayorCantidad;
import rankings.criterios.MayorTiempo;
import rankings.informes.*;
import repositiorios.DataRepositorios;

import java.util.List;


public class informesTest {
  private DataRepositorios repositorios;
  private List<Comunidad> comunidades;
  private List<Entidad> entidades;
  private GeneradorDeInformes generadorDeInformes;
  private  List<List<String>> informes;

  @BeforeEach
    public void setUp(){
      repositorios = new DataRepositorios();
      comunidades = repositorios.repoComunidades.getComunidades();
      entidades = repositorios.repoEntidades.getEntidades();

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
  }
