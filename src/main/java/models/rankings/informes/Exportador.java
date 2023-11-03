package models.rankings.informes;

import models.entidades.Entidad;
import lombok.Setter;
import models.rankings.estrategiaDeExportacion.EstrategiaDeExportacion;

import java.util.List;

public class Exportador {
  private GeneradorDeInformes exportable;
@Setter private EstrategiaDeExportacion estrategia;

  public Exportador(GeneradorDeInformes exportable, EstrategiaDeExportacion estrategia) {
    this.exportable = exportable;
    this.estrategia = estrategia;
  }

  public String exportarConEstrategia(List<Entidad> entidades, String nombreArchivo) {
    return this.estrategia.exportar(this.exportable.generarDatos(entidades), nombreArchivo);
  }
}
