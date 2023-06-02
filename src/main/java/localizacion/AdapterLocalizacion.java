package localizacion;

import apiCalls.georef.responseClases.ListadoProvincias;
import apiCalls.georef.responseClases.Ubicacion;
import apiCalls.georef.responseClases.UbicacionResponse;

import java.io.IOException;

public interface AdapterLocalizacion {
    public ListadoProvincias getListadoProvincias() throws IOException;
}
