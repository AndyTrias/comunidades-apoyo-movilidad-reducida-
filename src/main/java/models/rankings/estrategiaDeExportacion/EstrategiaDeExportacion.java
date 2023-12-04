package models.rankings.estrategiaDeExportacion;

import models.rankings.informes.Ranking;

import java.util.List;

public interface EstrategiaDeExportacion {
  public void exportar(List<Ranking> informe, String nombreArchivo);
}
