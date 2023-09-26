package noServer.localizacion;

import noServer.external.georef.responseClases.Localidad;
import noServer.external.georef.responseClases.Municipio;
import noServer.external.georef.responseClases.Provincia;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Embeddable
public class Ubicacion {
    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "municipio_id")
    private Municipio municipio;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "localidad_id")
    private Localidad localidad;
}
