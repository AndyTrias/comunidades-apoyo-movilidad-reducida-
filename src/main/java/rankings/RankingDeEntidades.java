package rankings;

import entidades.Entidad;

import java.util.List;

public interface RankingDeEntidades {
  public List<Entidad> generarRanking(List<Entidad> comunidades);

  public String getNombre();
}
