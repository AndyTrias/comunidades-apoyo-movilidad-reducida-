package models.rankings.estrategiaDeExportacion;

import models.rankings.informes.Ranking;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AdapterJson {
  public void exportarAJson(List<Ranking> lista, String rutaArchivo);
}
