package models.rankings.estrategiaDeExportacion;

import models.configs.Config;
import models.external.json.ServicioJson;
import lombok.Getter;
import lombok.Setter;
import models.rankings.informes.Ranking;

import java.io.IOException;
import java.util.List;
import java.util.Map;


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
