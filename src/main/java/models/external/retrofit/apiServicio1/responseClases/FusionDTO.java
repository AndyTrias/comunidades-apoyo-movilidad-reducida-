package models.external.retrofit.apiServicio1.responseClases;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FusionDTO {
    private EstadoFusion estado;
    private ComunidadDTO comunidad1;
    private ComunidadDTO comunidad2;
    private String fechaCreada;
}
