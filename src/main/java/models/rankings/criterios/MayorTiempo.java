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


    private float promedioTiempoDeCierre(Entidad entidad) {
        List<Incidente> incidentes = obtenerIncidentesDeEntidadEnlaSemana(entidad);

        OptionalDouble promedio = incidentes.stream()
            .mapToDouble(Incidente::tiempoActivo)
            .average();

        return (float) promedio.orElse(0.0);
    }
}
