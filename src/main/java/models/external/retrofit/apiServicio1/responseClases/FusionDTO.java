package models.external.apiServicio.responseClases;

import lombok.Data;

@Data
public class FusionDTO {
    private EstadoFusion estado;
    private ComunidadDTO comunidad1;
    private ComunidadDTO comunidad2;
    private String fechaCreada;
}
