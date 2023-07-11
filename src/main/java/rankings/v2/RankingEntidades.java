package rankings.v2;

import entidades.Entidad;
import incidentes.Incidente;

import java.util.ArrayList;
import java.util.List;

public abstract class RankingEntidades {
    public abstract List<?> generarRanking();

    protected List<Incidente> obtenerIncidentesDeEntidad(Entidad entidad) {
        List<Incidente> incidentes = new ArrayList<>();
        entidad.getEstablecimientos().forEach(e -> {
            e.getServicios().forEach(s -> {
                s.getIncidentes().addAll(incidentes);
            });
        });

        return incidentes;
    }
}
