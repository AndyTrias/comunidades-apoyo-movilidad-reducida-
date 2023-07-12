package rankings.informes;

import comunidades.Comunidad;
import entidades.Entidad;
import rankings.criterios.CriteriosDeEntidades;
import rankings.criterios.CriteriosDeComunidades;

import java.util.ArrayList;
import java.util.List;

public class GeneradorDeInformes {

  private List<CriteriosDeComunidades> criteriosDeComunidades;
  private List<CriteriosDeEntidades> criteriosDeEntidades;

  public GeneradorDeInformes() {
    this.criteriosDeComunidades = new ArrayList<>();
    this.criteriosDeEntidades = new ArrayList<>();
  }

  public void agregarCriterioDeComunidad(CriteriosDeComunidades criterio) {
    this.criteriosDeComunidades.add(criterio);
  }

  public void agregarCriterioDeEntidad(CriteriosDeEntidades criterio) {
    this.criteriosDeEntidades.add(criterio);
  }

  public List<List<String>> generarDatos(List<Entidad> entidades, List<Comunidad> comunidades) {
    List<List<String>> datos = new ArrayList<>();

    for (CriteriosDeEntidades criterio : criteriosDeEntidades) {
      List<String> filas = new ArrayList<>();
      filas.add(criterio.getNombre());
      filas.add(String.valueOf(criterio.generarRanking(entidades)));
      datos.add(filas);
    }

    for (CriteriosDeComunidades criterio : criteriosDeComunidades) {
      List<String> filas = new ArrayList<>();
      filas.add(String.valueOf(criterio.generarRanking(comunidades)));
      datos.add(filas);
    }
    return datos;
  }

}

