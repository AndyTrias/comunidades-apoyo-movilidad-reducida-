package rankings;

import incidentes.Incidente;
import entidades.Entidad;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingMayorTiempo extends RankingEntidadesQueUsanIncidentes {

    private float calcularPromedio(Entidad entidad){
        List<Incidente> incidentes = obtenerIncidentesDeEntidad(entidad);
        float suma = 0;
        for (Incidente incidente : incidentes) {
            suma += incidente.tiempoActivo();
        }
        return suma / incidentes.size();
    }

    @Override
    public List<Entidad> generarRanking(List<Entidad> entidades) {
        Collections.sort(entidades, Comparator.comparingDouble(this::calcularPromedio));
        return entidades;
    }

}
