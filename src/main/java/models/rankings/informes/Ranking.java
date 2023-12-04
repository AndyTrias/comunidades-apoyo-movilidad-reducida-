package models.rankings.informes;

import lombok.Data;
import models.entidades.Entidad;


public record Ranking(Entidad entidad, Integer valor) {
}
