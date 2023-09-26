package noServer.rankings.informes;

import noServer.comunidades.Comunidad;
import noServer.entidades.Entidad;
import noServer.rankings.criterios.CriteriosDeComunidades;
import noServer.rankings.criterios.CriteriosDeEntidades;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
      List<String> rankingNombres = criterio.generarRanking(entidades).stream()
          .map(Entidad::getNombre)
          .collect(Collectors.toList());
      String rankings = String.join(", ", rankingNombres);
      columnas.add(rankings);

      datos.add(columnas);
    }


    for (CriteriosDeComunidades criterio : criteriosDeComunidades) {
      List<String> columnas = new ArrayList<>();
      columnas.add(criterio.getNombre());

      List<String> rankingNombres = criterio.generarRanking(comunidades).stream()
          .map(Comunidad::getNombre)
          .collect(Collectors.toList());
      String rankings = String.join(", ", rankingNombres);
      columnas.add(rankings);

      datos.add(columnas);
    }
    return datos;
  }

}

