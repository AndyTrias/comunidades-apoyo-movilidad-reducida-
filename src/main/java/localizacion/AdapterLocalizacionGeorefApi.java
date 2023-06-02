package localizacion;

import apiCalls.georef.ServicioGeoref;
import apiCalls.georef.responseClases.ListadoProvincias;

import java.io.IOException;

public class AdapterLocalizacionGeorefApi implements AdapterLocalizacion {
    private ServicioGeoref adapterServicioGeoref = ServicioGeoref.getInstancia();

    public ListadoProvincias getListadoProvincias() throws IOException {
        return adapterServicioGeoref.listadoProvincias();
    }
}
