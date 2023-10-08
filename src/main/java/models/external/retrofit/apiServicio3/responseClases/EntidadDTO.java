package models.external.retrofit.apiServicio3.responseClases;

import lombok.Data;

import java.util.List;

@Data
public class EntidadDTO {
    private int id;
    private List<IncidenteDTO> incidentes;
}
