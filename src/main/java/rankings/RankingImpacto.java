package rankings;

import comunidades.Comunidad;
import comunidades.incidentes.Incidente;

import java.util.Comparator;
import java.util.List;

public class RankingImpacto implements RankingComunidades{

    private float calcularImpacto(Comunidad comunidad){
        List<Incidente> incidentes = comunidad.getTodosLosIncidentes();
        return (float) comunidad.getCantidadDeUsuarios() / incidentes.size();
    }

    public List<Comunidad> generarRanking(List<Comunidad> comunidades) {
        comunidades.sort(Comparator.comparing(this::calcularImpacto));
        return comunidades;
    }


}
