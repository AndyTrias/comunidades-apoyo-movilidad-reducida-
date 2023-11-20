package models.external.retrofit.apiServicio1.responseClases;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ComunidadDTO {
    private Integer id;
    private List<Integer> idEstablecimientoObservados;
    private List<Integer> idServiciosObservados;
    private int gradoDeConfianza;
    private List<Integer> idMiembros;
    private EstadoComunidad estado;

    public enum EstadoComunidad {
        ACTIVADA,
        DESACTIVADA
    }
}
