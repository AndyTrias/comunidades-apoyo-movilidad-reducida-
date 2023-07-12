package rankings;

import incidentes.Incidente;
import entidades.Entidad;
import repositiorios.RepoEntidades;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingMayorTiempo extends RankingEntidades {

    public List<Entidad> generarRanking(List<Entidad> entidades) {
//        List<Entidad> entidades = RepoEntidades.getInstance().getEntidades();
        Collections.sort(entidades, (entidad1, entidad2) -> Float.compare(promedioTiempoDeCierre(entidad1), promedioTiempoDeCierre(entidad2)));
        return entidades;
    }

    private float promedioTiempoDeCierre(Entidad entidad){
        List<Incidente> incidentes = obtenerIncidentesDeEntidad(entidad);
        float suma = 0;
        for (Incidente incidente : incidentes) {
            suma += incidente.tiempoActivo();
        }
        return suma / incidentes.size();
    }

}
