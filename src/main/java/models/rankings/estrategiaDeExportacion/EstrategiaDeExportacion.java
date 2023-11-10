package models.rankings.estrategiaDeExportacion;

import java.io.IOException;
import java.util.List;

public interface EstrategiaDeExportacion {
  public String exportar(List<List<String>> informe, String nombreArchivo) ;
}
