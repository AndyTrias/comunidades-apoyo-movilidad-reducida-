package models.external.retrofit.apiServicio3.responseClases;

import lombok.Data;

@Data
public class IncidenteDTO {
    private String fechaApertura;
    private String fechaCierre;
    private int miembrosAfectados;
}
