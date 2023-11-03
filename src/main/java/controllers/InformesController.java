package controllers;

import lombok.AllArgsConstructor;
import models.external.json.ServicioJson;
import models.rankings.criterios.MayorCantidad;
import models.rankings.criterios.MayorTiempo;
import models.rankings.estrategiaDeExportacion.EstrategiaDeExportacion;
import models.rankings.estrategiaDeExportacion.ExportarCSV;
import models.rankings.informes.Exportador;
import models.rankings.estrategiaDeExportacion.ExportarAJson;
import models.rankings.informes.GeneradorDeInformes;
import models.repositorios.RepoEntidad;

import java.time.LocalDate;

@AllArgsConstructor
public class InformesController {

    private RepoEntidad repoEntidad;

    public void generarRankings() {
        EstrategiaDeExportacion estrategia = new ExportarAJson(new ServicioJson());

        GeneradorDeInformes generadorDeInformes = new GeneradorDeInformes();
        generadorDeInformes.agregarCriterioDeEntidad(new MayorTiempo("Tiempo de resolucion"));
        generadorDeInformes.agregarCriterioDeEntidad(new MayorCantidad("Cantidad de incidentes"));

        Exportador exportador = new Exportador(generadorDeInformes, estrategia);
        exportador.exportarConEstrategia(repoEntidad.buscarTodos(), "ranking_" + LocalDate.now() + ".json");
    }

}
