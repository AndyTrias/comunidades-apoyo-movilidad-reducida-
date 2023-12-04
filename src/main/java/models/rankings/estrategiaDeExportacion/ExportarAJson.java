package models.rankings.estrategiaDeExportacion;

import lombok.Getter;
import lombok.Setter;
import models.external.json.ServicioJson;
import models.rankings.informes.Ranking;

import java.util.List;


public class ExportarAJson implements EstrategiaDeExportacion {

  @Getter @Setter
  private ServicioJson adapter;

  public ExportarAJson(ServicioJson adapter) {
    this.adapter = adapter;
  }

  public void exportar(List<Ranking> lista, String rutaArchivo) {
    adapter.exportarAJson(lista, rutaArchivo);
  }

}
