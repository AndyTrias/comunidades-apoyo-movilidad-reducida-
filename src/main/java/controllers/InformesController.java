package controllers;

import lombok.AllArgsConstructor;
import models.comunidades.Comunidad;
import models.entidades.Entidad;
import models.external.json.ServicioJson;
import models.rankings.criterios.CriteriosDeEntidades;
import models.rankings.criterios.MayorCantidad;
import models.rankings.criterios.MayorTiempo;
import models.rankings.informes.EstrategiaDeExportacion;
import models.rankings.informes.Exportador;
import models.rankings.informes.ExportarAJson;
import models.rankings.informes.GeneradorDeInformes;
import models.repositorios.RepoEntidad;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class InformesController {

    private RepoEntidad repoEntidad;

    public void generarRankings(List<CriteriosDeEntidades> criteriosEntidades) {
        EstrategiaDeExportacion estrategia = new ExportarAJson(new ServicioJson());

        GeneradorDeInformes generadorDeInformes = new GeneradorDeInformes();
        generadorDeInformes.agregarCriteriosDeEntidad(criteriosEntidades);

        Exportador exportador = new Exportador(generadorDeInformes, estrategia);
        exportador.exportarConEstrategia(repoEntidad.buscarTodos(), "ranking_" + LocalDate.now() + ".json");
    }

}
