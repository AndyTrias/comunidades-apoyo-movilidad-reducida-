package models.rankings.criterios;

import models.incidentes.Incidente;
import models.entidades.Entidad;

import java.util.Collections;
import java.util.List;

public class MayorTiempo extends CriteriosEntidadesQueUsanIncidentes {

    public MayorTiempo(String nombre) {
        super(nombre);
    }

    public List<Entidad> generarRanking(List<Entidad> entidades) {
        entidades.sort((entidad1, entidad2) ->
            Float.compare(promedioTiempoDeCierre(entidad1), promedioTiempoDeCierre(entidad2)));

        return entidades;
    }

    private float promedioTiempoDeCierre(Entidad entidad) {
        List<Incidente> incidentes = obtenerIncidentesDeEntidad(entidad);
        if (incidentes.isEmpty()) {
            return 0;
        }

        float suma = 0;
        for (Incidente incidente : incidentes) {
            suma += incidente.tiempoActivo();
        }
        return suma / incidentes.size();
    }
}
