package rankings;

import comunidades.Comunidad;
import entidades.Entidad;

import java.util.ArrayList;
import java.util.List;

public class GeneradorDeInformes {

  private List<RankingsDeComunidades> criteriosDeComunidades;
  private List<RankingDeEntidades> criteriosDeEntidades;

  public GeneradorDeInformes() {
    this.criteriosDeComunidades = new ArrayList<>();
    this.criteriosDeEntidades = new ArrayList<>();
  }

  public void agregarCriterioDeComunidad(RankingsDeComunidades criterio) {
    this.criteriosDeComunidades.add(criterio);
  }

  public void agregarCriterioDeEntidad(RankingDeEntidades criterio) {
    this.criteriosDeEntidades.add(criterio);
  }

  public List<List<String>> generarDatos(List<Entidad> entidades, List<Comunidad> comunidades) {
    List<List<String>> datos = new ArrayList<>();

    for (RankingDeEntidades criterio : criteriosDeEntidades) {
      List<String> fila = new ArrayList<>();
      fila.add(criterio.getNombre());
      fila.add(String.valueOf(criterio.generarRanking(entidades)));
      datos.add(fila);
    }

    for (RankingsDeComunidades criterio : criteriosDeComunidades) {
      List<String> fila = new ArrayList<>();
      fila.add(String.valueOf(criterio.generarRanking(comunidades)));
      datos.add(fila);
    }
    return datos;
  }

}
 



// NombreCriterio Lista
// Ranking Tiempo 
// Ranking Impacto
// Ranking Comunidades


