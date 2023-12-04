package models.rankings.criterios;

import models.entidades.Entidad;
import models.rankings.informes.Ranking;

import java.util.List;

public interface CriteriosDeEntidades {
  List<Ranking> generarRanking(List<Entidad> entidades);

  String getNombre();

  String getNombreInterno();
}
