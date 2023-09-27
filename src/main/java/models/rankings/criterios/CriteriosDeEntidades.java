package models.rankings.criterios;

import models.entidades.Entidad;

import java.util.List;

public interface CriteriosDeEntidades {
  public List<Entidad> generarRanking(List<Entidad> comunidades);

  public String getNombre();
}
