package rankings;

import comunidades.Comunidad;

import java.util.List;

public interface RankingsDeComunidades {
  public List<Comunidad> generarRanking(List<Comunidad> comunidades);

  public String getNombre();
}
