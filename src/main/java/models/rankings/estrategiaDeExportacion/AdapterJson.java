package models.rankings.estrategiaDeExportacion;

import models.rankings.informes.Ranking;

import java.util.List;

public interface AdapterJson {
  void exportarAJson(List<Ranking> lista, String rutaArchivo);
}
