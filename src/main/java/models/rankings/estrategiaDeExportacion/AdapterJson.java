package models.rankings.estrategiaDeExportacion;

import java.io.IOException;
import java.util.List;

public interface AdapterJson {
  public String exportarAJson(List<List<String>> lista, String rutaArchivo) throws IOException;
}
