package models.dto;

import com.mysql.cj.xdevapi.JsonString;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class IncidenteDeComunidadDTO {
    private Long prestacionId;
    private Date fechaDeApertura;
    private String observaciones;
}
