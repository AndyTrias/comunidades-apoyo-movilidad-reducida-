package rankings.informes;

import comunidades.Comunidad;
import entidades.Entidad;
import lombok.Setter;

import java.util.List;

public class Exportador {
  private GeneradorDeInformes exportable;
@Setter private EstrategiaDeExportacion estrategia;

  public Exportador(GeneradorDeInformes exportable, EstrategiaDeExportacion estrategia) {
    this.exportable = exportable;
    this.estrategia = estrategia;
  }


  public void exportarConEstrategia(List<Entidad> entidades, List<Comunidad> comunidades) {
    //this.estrategia.exportar(this.exportable.generarDatos(entidades, comunidades));
  }
  public String exportarConEstrategia(List<Entidad> entidades, List<Comunidad> comunidades, String nombreArchivo) {
    return this.estrategia.exportar(this.exportable.generarDatos(entidades, comunidades), nombreArchivo);

  }
}
