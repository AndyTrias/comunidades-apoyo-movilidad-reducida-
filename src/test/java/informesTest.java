import comunidades.Comunidad;
import entidades.Entidad;
import external.json.ServicioJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

      List<List<String>> informes = generadorDeInformes.generarDatos(entidades, comunidades);
    }

    @Test
    public void soyUnTest(){
      EstrategiaDeExportacion estrategia = new ExportarCSV();
      Exportador exportador = new Exportador(generadorDeInformes);
      exportador.exportarConEstrategia(entidades, comunidades, "informes.csv");
    }

    @Test
    public void soyOtroTest() {
      EstrategiaDeExportacion estrategiaDeExportacion = new ExportarAJson(new ServicioJson());
      Exportador exportador = new Exportador(generadorDeInformes, estrategiaDeExportacion);
      exportador.exportarConEstrategia(entidades, comunidades, "informes.json");
    }
  }
