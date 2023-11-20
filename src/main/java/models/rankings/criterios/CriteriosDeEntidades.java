package models.rankings.criterios;

import models.entidades.Entidad;

import java.util.List;
import java.util.Map;

public interface CriteriosDeEntidades {
  List<Entidad> generarRanking(List<Entidad> entidades);

  String getNombre();

  String getNombreInterno();
}
