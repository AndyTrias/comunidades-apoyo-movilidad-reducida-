package models.rankings.informes;

import models.configs.Config;
import models.external.json.ServicioJson;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class ExportarAJson implements EstrategiaDeExportacion {

  @Getter @Setter
  private ServicioJson adapter;

  public ExportarAJson(ServicioJson adapter) {
    this.adapter = adapter;
  }

  public String exportar(List<List<String>> lista, String rutaArchivo)
  {
    return adapter.exportarAJson(lista, Config.PATH_INFORMES + rutaArchivo);
  }

}
