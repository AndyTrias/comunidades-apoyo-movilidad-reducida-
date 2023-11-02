package controllers;

import lombok.AllArgsConstructor;
import models.comunidades.Comunidad;
import models.entidades.Entidad;
import models.rankings.criterios.CriteriosDeComunidades;
import models.rankings.criterios.CriteriosDeEntidades;
import models.rankings.informes.GeneradorDeInformes;

import java.util.List;

@AllArgsConstructor
public class InformesController {

//lo que tengo es el repositorio con los informes buscados por fecha
    // necesito los datos para pasarle al generador de informes



    public List<List<String>> generarInformes(List<CriteriosDeComunidades> criteriosComunidades, List<CriteriosDeEntidades> criteriosEntidades, List<Comunidad> comunidades, List<Entidad> entidades) {
        GeneradorDeInformes generador = new GeneradorDeInformes();

        // Agregar los criterios al generador
        for (CriteriosDeComunidades criterio : criteriosComunidades) {
            generador.agregarCriterioDeComunidad(criterio);
        }

        for (CriteriosDeEntidades criterio : criteriosEntidades) {
            generador.agregarCriterioDeEntidad(criterio);
        }

        // Generar los datos del informe
        List<List<String>> informes = generador.generarDatos(entidades, comunidades);

        return informes;

    }

}
