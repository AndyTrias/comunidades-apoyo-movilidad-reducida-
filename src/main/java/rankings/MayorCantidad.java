package rankings;

import incidentes.Incidente;
import entidades.Entidad;

import java.util.List;
import java.util.stream.Collectors;

public class MayorCantidad extends RankingEntidadesQueUsanIncidentes {



  public MayorCantidad(String nombre) {
    super(nombre);
  }

  public List<Entidad> generarRanking(List<Entidad> entidades) {
    return entidades.stream().sorted((entidad1, entidad2) -> Integer.compare(cantidadDeIncidentesEnLaSemana(entidad1), cantidadDeIncidentesEnLaSemana(entidad2))).collect(Collectors.toList());
  }

  private int cantidadDeIncidentesEnLaSemana(Entidad entidad) {
    List<Incidente> incidentes = obtenerIncidentesDeEntidad(entidad);
    List<Incidente> incidentesValidos = incidentes.stream().filter(i -> incidenteNoOcurrioHace24Hs(i, incidentes) && i.ocurrioEstaSemana()).toList();
    return incidentesValidos.size();
  }

  private boolean incidenteNoOcurrioHace24Hs(Incidente incidente, List<Incidente> incidentesPrestacion) {
    return incidentesPrestacion.stream()
        .noneMatch(incidentePrestacion ->
            incidentePrestacion
                .getFechaDeApertura()
                .getTime() - incidente.getFechaDeApertura().getTime() < 24 * 60 * 60 * 1000 && incidentePrestacion.estaAbierto());
  }
}


