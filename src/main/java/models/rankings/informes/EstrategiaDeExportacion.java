package models.rankings.informes;

import java.util.List;

public interface EstrategiaDeExportacion {
  public String exportar(List<List<String>> informe, String nombreArchivo);
}
