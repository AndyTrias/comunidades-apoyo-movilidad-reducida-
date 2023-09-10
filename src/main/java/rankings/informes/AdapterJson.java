package rankings.informes;

import java.util.List;

public interface AdapterJson {
  public String exportarAJson(List<List<String>> lista, String rutaArchivo);
}
