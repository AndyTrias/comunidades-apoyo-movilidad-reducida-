package models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import models.entidades.Entidad;
import models.servicios.Servicio;

import java.util.List;

@Data
@AllArgsConstructor
public class InteresDTO {
    private Entidad entidad;
    private List<Servicio> servicios;

}
