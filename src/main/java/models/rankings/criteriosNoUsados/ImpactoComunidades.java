package models.rankings.criteriosNoUsados;

import models.comunidades.Comunidad;
import lombok.Getter;
import models.incidentes.IncidenteDeComunidad;
import models.rankings.criteriosNoUsados.CriteriosDeComunidades;

import java.util.Comparator;
import java.util.List;

public class ImpactoComunidades implements CriteriosDeComunidades {

    @Getter private String nombre;

    public ImpactoComunidades(String nombre) {
        this.nombre = nombre;
    }

    private float calcularImpacto(Comunidad comunidad){
        List<IncidenteDeComunidad> incidentes = comunidad.getIncidentes();
        return (float) comunidad.getMembresias().size() / incidentes.size();
    }


    public List<Comunidad> generarRanking(List<Comunidad> comunidades){
        comunidades.sort(Comparator.comparing(this::calcularImpacto));
        return comunidades;
    }

}
