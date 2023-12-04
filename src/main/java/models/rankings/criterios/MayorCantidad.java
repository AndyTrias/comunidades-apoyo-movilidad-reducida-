package models.rankings.criterios;

import models.entidades.Entidad;
import models.incidentes.Incidente;
import models.rankings.informes.Ranking;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MayorCantidad extends CriteriosEntidadesQueUsanIncidentes {

  public MayorCantidad(String nombre) {
    super(nombre);
  }

  public List<Ranking> generarRanking(List<Entidad> entidades) {
    return entidades.stream()
        .sorted(Comparator.comparingInt(this::cantidadDeIncidentesEnLaSemana))
        .map(entidad -> new Ranking(entidad, cantidadDeIncidentesEnLaSemana(entidad)))
        .collect(Collectors.toList());
  }

  public int cantidadDeIncidentesEnLaSemana(Entidad entidad) {
    return (int) obtenerIncidentesDeEntidadEnlaSemana(entidad)
        .stream()
        .filter(Incidente::ocurrioHaceMasDe24Hs)
        .count();
  }



}


