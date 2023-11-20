package models.rankings.criterios;

import models.entidades.Entidad;
import models.incidentes.Incidente;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MayorCantidad extends CriteriosEntidadesQueUsanIncidentes {

  public MayorCantidad(String nombre) {
    super(nombre);
  }

  public List<Entidad> generarRanking(List<Entidad> entidades) {
    return entidades.stream()
        .sorted(Comparator.comparingInt(this::cantidadDeIncidentesEnLaSemana))
        .collect(Collectors.toList());
  }

  private int cantidadDeIncidentesEnLaSemana(Entidad entidad) {
    return (int) obtenerIncidentesDeEntidadEnlaSemana(entidad)
        .stream()
        .filter(Incidente::ocurrioHaceMasDe24Hs)
        .count();
  }



}


