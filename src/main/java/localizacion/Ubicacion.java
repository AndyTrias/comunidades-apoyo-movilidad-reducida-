package localizacion;

import apiCalls.georef.responseClases.Localidad;
import apiCalls.georef.responseClases.Municipio;
import apiCalls.georef.responseClases.Provincia;
import lombok.Getter;
import lombok.Setter;

public class Ubicacion {
    @Getter @Setter private Provincia provincia;
    @Getter @Setter private Municipio municipio;
    @Getter @Setter private Localidad localidad;
}
