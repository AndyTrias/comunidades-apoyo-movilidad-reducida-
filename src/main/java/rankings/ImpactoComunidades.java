package rankings;

import comunidades.Comunidad;
import incidentes.Incidente;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;

public class ImpactoComunidades implements RankingsDeComunidades {

    @Getter private String nombre;


    private float calcularImpacto(Comunidad comunidad){
        List<Incidente> incidentes = comunidad.getTodosLosIncidentes();
        return (float) comunidad.getCantidadDeUsuarios() / incidentes.size();
    }


    public List<Comunidad> generarRanking(List<Comunidad> comunidades){
        comunidades.sort(Comparator.comparing(this::calcularImpacto));
        return comunidades;
    }

}
