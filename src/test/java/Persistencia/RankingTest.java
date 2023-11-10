package Persistencia;

import controllers.InformesController;
import models.repositorios.RepoEntidad;
import models.repositorios.RepoInformes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RankingTest {
  private RepoInformes repoInformes;
  private RepoEntidad repoEntidad;

  @BeforeEach
  public void setUp() {
    repoInformes = new RepoInformes();
    repoEntidad = new RepoEntidad();
  }

  @Test
  public void TestRanking() {
    new InformesController(repoEntidad, repoInformes).generarRankings();
  }
}
