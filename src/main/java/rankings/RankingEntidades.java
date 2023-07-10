package rankings;


import incidentes.Incidente;
import servicios.PrestacionDeServicio;
import entidades.Entidad;

import java.util.ArrayList;
import java.util.List;

public abstract class RankingEntidades {

    public List<Entidad> generarRanking(List<Entidad> entidades) {
        return null;
    }

    protected boolean validarIncidente(Incidente incidente, List<Incidente> incidentesPrestacion) {
        return true;
    }

    protected List<Incidente> obtenerIncidentesDeEntidad(Entidad entidad) {
        //create an empty list of incidents

        List<Incidente> incidentes = new ArrayList<Incidente>();
        for (PrestacionDeServicio prestacion : entidad.getPrestacionesDeServicios()) {
            List<Incidente> incidentesPrestacion = new ArrayList<>();
            for (Incidente incidente : prestacion.getIncidentes()) {

                //si no existe ningun incidente en la lista incidentesPrestacion cuya fecha de apertura tenga una diferencia de menos de 24 horas, agregar a la lista
                if (this.validarIncidente(incidente, incidentesPrestacion)) {
                    incidentesPrestacion.add(incidente);
                }
            }
            incidentes.addAll(incidentesPrestacion);
        }
        return incidentes;
    }
}
