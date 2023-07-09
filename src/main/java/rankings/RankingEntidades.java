package rankings;

import java.util.ArrayList;

public abstract class RankingEntidades {



    public List<Entidad> generarRanking(List<Entidad> entidades) {
        return null;
    }

    protected boolean validarIncidente(Incidente incidente, List<Incidente> incidentesPrestacion) {
        return True;
    }

    protected List<Incidentes> obtenerIncidentesDeEntidad(Entidad entidad) {
        //create an empty list of incidents

        List<Incidentes> incidentes = new ArrayList<Incidentes>();
        //foreach entity.getprestacionesdeservicios
        List<PrestacionDeServicio> prestaciones = new ArrayList<>(PrestacionDeServicio);
        prestaciones.addAll(entidad.getPrestacionesDeServicios());
        for (PrestacionDeServicio prestacion : prestaciones) {
            List<Incidentes> incidentesPrestacion = new ArrayList<>(Incidente);
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
