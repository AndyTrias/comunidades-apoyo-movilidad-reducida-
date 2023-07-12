package rankings.criterios;


import incidentes.Incidente;
import lombok.Getter;
import servicios.PrestacionDeServicio;
import entidades.Entidad;

import java.util.ArrayList;
import java.util.List;

public abstract class CriteriosEntidadesQueUsanIncidentes implements CriteriosDeEntidades {

    @Getter private String nombre;

    public CriteriosEntidadesQueUsanIncidentes(String nombre) {
        this.nombre = nombre;
    }

    public abstract List<Entidad> generarRanking(List<Entidad> entidades);

    protected List<Incidente> obtenerIncidentesDeEntidad(Entidad entidad) {

        List<Incidente> incidentes = new ArrayList<>();
        for (PrestacionDeServicio prestacion : entidad.getPrestacionesDeServicios()) {
            List<Incidente> incidentesPrestacion = new ArrayList<>(prestacion.getIncidentes());
            incidentes.addAll(incidentesPrestacion);
        }
        return incidentes;
    }
}
