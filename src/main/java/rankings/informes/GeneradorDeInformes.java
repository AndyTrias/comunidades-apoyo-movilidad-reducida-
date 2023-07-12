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

    List<String> encabezados = new ArrayList<>();
    encabezados.add("Criterio");
    encabezados.add("Ranking");
    datos.add(encabezados);

    for (CriteriosDeEntidades criterio : criteriosDeEntidades) {
      List<String> columnas = new ArrayList<>();
      columnas.add(criterio.getNombre());
      String rankings = String.valueOf(criterio.generarRanking(entidades));
      rankings = rankings.substring(1, rankings.length() - 1);
      columnas.add(rankings.replace(", ", ", "));

      datos.add(columnas);
    }

    for (CriteriosDeComunidades criterio : criteriosDeComunidades) {
      List<String> columnas = new ArrayList<>();
      columnas.add(criterio.getNombre());
      String rankings = String.valueOf(criterio.generarRanking(comunidades));
      rankings = rankings.substring(1, rankings.length() - 1);
      columnas.add(rankings.replace(", ", ", "));
      datos.add(columnas);
    }
    return datos;
  }

}

