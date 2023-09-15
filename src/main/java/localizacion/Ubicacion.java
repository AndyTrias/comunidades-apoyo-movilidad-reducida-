package localizacion;

import external.georef.responseClases.Localidad;
import external.georef.responseClases.Municipio;
import external.georef.responseClases.Provincia;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Embeddable
public class Ubicacion {
    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "municipio_id")
    private Municipio municipio;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "localidad_id")
    private Localidad localidad;
}
