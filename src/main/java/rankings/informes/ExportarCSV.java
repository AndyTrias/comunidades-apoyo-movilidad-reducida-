package rankings.informes;

import configs.Config;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportarCSV implements EstrategiaDeExportacion {
  public String exportar(List<List<String>> informe, String nombreArchivo) {
    String csvFile = Config.PATH_INFORMES + nombreArchivo;

    try (FileWriter writer = new FileWriter(csvFile)) {
      for (List<String> fila : informe) {
        writer.append(fila.get(0));
        writer.append(",");
        writer.append(fila.get(1));
        writer.append("\n");
      }

    } catch (IOException e) {
      e.getStackTrace();
    }
    return csvFile;
  }


}
