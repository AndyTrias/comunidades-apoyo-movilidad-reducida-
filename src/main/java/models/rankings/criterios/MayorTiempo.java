package models.rankings.criterios;

import models.incidentes.Incidente;
import models.entidades.Entidad;

import java.util.*;
import java.util.stream.Collectors;

public class MayorTiempo extends CriteriosEntidadesQueUsanIncidentes {

    public MayorTiempo(String nombre) {
        super(nombre);
    }

    public List<Entidad> generarRanking(List<Entidad> entidades) {
        return entidades.stream()
            .sorted(Comparator.comparingDouble(this::promedioTiempoDeCierre))
            .collect(Collectors.toList());
    }


    public float promedioTiempoDeCierre(Entidad entidad) {
        return (float) obtenerIncidentesDeEntidadEnlaSemana(entidad).stream()
            .mapToDouble(Incidente::tiempoActivo)
            .average()
            .orElse(0.0);
    }
}
