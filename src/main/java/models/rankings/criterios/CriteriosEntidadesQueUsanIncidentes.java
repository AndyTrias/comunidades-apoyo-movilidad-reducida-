package models.rankings.criterios;


import models.incidentes.Incidente;
import lombok.Getter;
import models.servicios.PrestacionDeServicio;
import models.entidades.Entidad;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CriteriosEntidadesQueUsanIncidentes implements CriteriosDeEntidades {

    @Getter private String nombre;

    public CriteriosEntidadesQueUsanIncidentes(String nombre) {
        this.nombre = nombre;
    }

    public abstract List<Entidad> generarRanking(List<Entidad> entidades);

    protected List<Incidente> obtenerIncidentesDeEntidadEnlaSemana(Entidad entidad) {
        return entidad.getPrestacionesDeServicios().stream()
            .flatMap(prestacion -> prestacion.getIncidentes().stream())
            .filter(Incidente::ocurrioEstaSemana)
            .collect(Collectors.toList());
    }

    public String getNombreInterno() {
        return this.nombre.replace(" ", "_");
    }
}
