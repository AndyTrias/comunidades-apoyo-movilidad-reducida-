package localizacion;

import apiCalls.georef.ServicioGeoref;
import apiCalls.georef.responseClases.Ubicacion;

import java.io.IOException;

public class AdapterLocalizacionGeorefApi implements AdapterLocalizacion {
    private ServicioGeoref adapterServicioGeoref = ServicioGeoref.getInstancia();

    // 2 optional parameters
    public Ubicacion getUbicacion(String tipoDeLocalizacion, String id) throws IOException {
        return adapterServicioGeoref.getUbicacion(tipoDeLocalizacion, id);
    }
}
