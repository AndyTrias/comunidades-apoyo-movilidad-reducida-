package localizacion;

import external.georef.responseClases.Localidad;
import external.georef.responseClases.Municipio;
import external.georef.responseClases.Provincia;
import lombok.Getter;
import lombok.Setter;

public class Ubicacion {
    @Getter @Setter private Provincia provincia;
    @Getter @Setter private Municipio municipio;
    @Getter @Setter private Localidad localidad;
}
