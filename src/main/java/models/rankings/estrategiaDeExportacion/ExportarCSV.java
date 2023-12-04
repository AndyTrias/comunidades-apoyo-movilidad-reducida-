package models.rankings.estrategiaDeExportacion;

import models.configs.Config;
import models.rankings.informes.Ranking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;


public class ExportarCSV implements EstrategiaDeExportacion {

  public void exportar(List<Ranking> informe, String nombreArchivo) {
    String csvFilePath = Config.getInstance().PATH_INFORMES + nombreArchivo;
    try {
      PrintWriter pw = new PrintWriter(new File(csvFilePath));
      for (Ranking ranking : informe) {
        pw.println(ranking.entidad().getId() + "," + ranking.valor());
      }
      pw.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
