package rankings.v2;

import entidades.Entidad;
import incidentes.Incidente;
import repositiorios.RepoEntidades;

import java.util.List;
import java.util.stream.Collectors;

public class RankingCantidadIncidentes extends Ranking {

    public List<Entidad> generarRanking() {
        List<Entidad> entidades = RepoEntidades.getInstance().getEntidades();
        return entidades.stream().sorted((entidad1, entidad2) -> Integer.compare(cantidadDeIncidentesEnLaSemana(entidad1), cantidadDeIncidentesEnLaSemana(entidad2))).collect(Collectors.toList());
    }

    private int cantidadDeIncidentesEnLaSemana(Entidad entidad){
        List<Incidente> incidentes = obtenerIncidentesDeEntidad(entidad);
        List<Incidente> incidentesValidos = incidentes.stream().filter(incidente -> incidenteOcurrioHace24Hs(incidente, incidentes)).toList();
        return incidentesValidos.size();
    }

    private boolean incidenteOcurrioHace24Hs(Incidente incidente, List<Incidente> incidentesPrestacion) {
        return incidentesPrestacion.stream()
                .noneMatch(incidentePrestacion ->
                        incidentePrestacion
                                .getFechaDeApertura()
                                .getTime() - incidente.getFechaDeApertura().getTime() < 24 * 60 * 60 * 1000 && incidentePrestacion.estaAbierto()) ;
    }
}
