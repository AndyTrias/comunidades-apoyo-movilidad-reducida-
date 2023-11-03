package models.rankings.criterios;

import models.entidades.Entidad;

import java.util.List;
import java.util.Map;

public interface CriteriosDeEntidades {
  public List<Entidad> generarRanking(List<Entidad> entidades);

  public String getNombre();
}
