package models.rankings.informes;

import models.entidades.Entidad;
import models.rankings.criterios.CriteriosDeEntidades;

import java.util.ArrayList;
import java.util.List;

public class GeneradorDeInformes {


  public List<Ranking> generarDatos(List<Entidad> entidades, CriteriosDeEntidades criterio) {

    return new ArrayList<>(criterio.generarRanking(entidades));
  }

}


