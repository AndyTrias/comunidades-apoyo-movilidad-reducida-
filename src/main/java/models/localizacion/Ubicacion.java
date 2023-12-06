package models.localizacion;

import models.external.retrofit.georef.responseClases.Localidad;
import models.external.retrofit.georef.responseClases.Municipio;
import models.external.retrofit.georef.responseClases.Provincia;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Embeddable
public class Ubicacion {
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "municipio_id")
    private Municipio municipio;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "localidad_id")
    private Localidad localidad;
}
