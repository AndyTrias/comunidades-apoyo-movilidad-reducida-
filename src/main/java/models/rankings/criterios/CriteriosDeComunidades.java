package models.rankings.criterios;

import models.comunidades.Comunidad;

import java.util.List;

public interface CriteriosDeComunidades {
  public List<Comunidad> generarRanking(List<Comunidad> comunidades);

  public String getNombre();
}
