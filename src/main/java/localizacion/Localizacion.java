package localizacion;

import apiCalls.georef.responseClases.ListadoProvincias;
import apiCalls.georef.responseClases.Provincia;
import apiCalls.georef.responseClases.Ubicacion;
import apiCalls.georef.responseClases.UbicacionResponse;
import lombok.Setter;

public class Localizacion {
    @Setter
    AdapterLocalizacion adapter;
    private Provincia provincia;

    public ListadoProvincias getListadoProvincias() throws Exception {
        return adapter.getListadoProvincias();
    }
}
