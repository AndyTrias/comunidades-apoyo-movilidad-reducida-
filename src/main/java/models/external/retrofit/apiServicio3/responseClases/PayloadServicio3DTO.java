package models.external.retrofit.apiServicio3.responseClases;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PayloadServicio3DTO {
    private List<EntidadDTO> entidades;
}
