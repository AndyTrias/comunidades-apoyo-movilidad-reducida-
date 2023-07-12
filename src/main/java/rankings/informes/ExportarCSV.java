package rankings.informes;

import configs.Config;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportarCSV implements EstrategiaDeExportacion {
  public void exportar(List<List<String>> informe) {
    String csvFile = Config.CSV_PATH_INFORMES;

    try (FileWriter writer = new FileWriter(csvFile)) {
      int maxRows = getMaxRows(informe);

      for (int i = 0; i < maxRows; i++) {
        for (List<String> columna : informe) {
          if (i < columna.size()) {
            writer.append(columna.get(i));
          }
          writer.append(",");
        }
        writer.append("\n");
      }

      System.out.println("Archivo CSV generado exitosamente.");
    } catch (IOException e) {
      throw new RuntimeException("Error al generar el archivo CSV: " + e.getMessage());
    }
  }

  private static int getMaxRows(List<List<String>> informe) {
    int cantidadMaxColumnas = 0;

    for (List<String> columna : informe) {
      if (columna.size() > cantidadMaxColumnas) {
        cantidadMaxColumnas = columna.size();
      }
    }
    return cantidadMaxColumnas;
  }

}
