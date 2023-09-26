package noServer.rankings.criterios;

import noServer.comunidades.Comunidad;
import noServer.incidentes.Incidente;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;

public class ImpactoComunidades implements CriteriosDeComunidades {

    @Getter private String nombre;

    public ImpactoComunidades(String nombre) {
        this.nombre = nombre;
    }

    private float calcularImpacto(Comunidad comunidad){
        List<Incidente> incidentes = comunidad.getTodosLosIncidentes();
        return (float) comunidad.getCantidadDeUsuarios() / incidentes.size();
    }


    public List<Comunidad> generarRanking(List<Comunidad> comunidades){
        comunidades.sort(Comparator.comparing(this::calcularImpacto));
        return comunidades;
    }

}
