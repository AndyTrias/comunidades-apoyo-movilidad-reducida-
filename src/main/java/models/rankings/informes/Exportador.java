package models.rankings.informes;

import models.entidades.Entidad;
import lombok.Setter;
import models.rankings.criterios.CriteriosDeEntidades;
import models.rankings.estrategiaDeExportacion.EstrategiaDeExportacion;

import java.util.List;

public class Exportador {
 @Setter
 private GeneradorDeInformes exportable;
@Setter
private EstrategiaDeExportacion estrategia;

  public Exportador(GeneradorDeInformes exportable, EstrategiaDeExportacion estrategia) {
    this.exportable = exportable;
    this.estrategia = estrategia;
  }

  public void exportarConEstrategia(List<Entidad> entidades, CriteriosDeEntidades criterio, String nombreArchivo) {
    this.estrategia.exportar(this.exportable.generarDatos(entidades, criterio), nombreArchivo);
  }
}
