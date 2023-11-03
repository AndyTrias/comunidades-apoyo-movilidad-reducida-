package models.rankings.estrategiaDeExportacion;

import java.util.List;

public interface AdapterJson {
  public String exportarAJson(List<List<String>> lista, String rutaArchivo);
}
