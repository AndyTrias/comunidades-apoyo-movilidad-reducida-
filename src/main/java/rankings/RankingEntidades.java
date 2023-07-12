package rankings;


import incidentes.Incidente;
import servicios.PrestacionDeServicio;
import entidades.Entidad;

import java.util.ArrayList;
import java.util.List;

public abstract class RankingEntidades {

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
