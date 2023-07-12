package rankings.criterios;

import comunidades.Comunidad;
import incidentes.Incidente;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;

public class ImpactoComunidades implements CriteriosDeComunidades {

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
