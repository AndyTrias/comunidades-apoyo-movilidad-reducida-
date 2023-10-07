package models.external.apiServicio.responseClases;

import lombok.Getter;
import models.comunidades.Comunidad;
import models.comunidades.Fusion;

import java.util.List;

@Getter
public class PayloadDTO {
    private List<ComunidadDTO> comunidades;
    private List<FusionDTO> fusiones;

    public PayloadDTO(List<ComunidadDTO> comunidades, List<FusionDTO> fusiones) {
        this.comunidades = comunidades;
        this.fusiones = fusiones;
    }
}
