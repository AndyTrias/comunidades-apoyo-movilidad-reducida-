package rankings.informes;

import external.json.ServicioJson;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class ExportarAJson implements EstrategiaDeExportacion {

  @Getter @Setter
  private ServicioJson adapter;

  public ExportarAJson(ServicioJson adapter) {
    this.adapter = adapter;
  }

  public void exportar(List<List<String>> lista, String rutaArchivo)
  {
    adapter.exportarAJson(lista, rutaArchivo);
  }

}
