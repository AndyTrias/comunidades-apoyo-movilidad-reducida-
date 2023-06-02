package localizacion;

import apiCalls.georef.ServicioGeoref;

public class AdapterLocalizacionGeorefApi implements AdapterLocalizacion {
    private ServicioGeoref adapterServicioGeoref = ServicioGeoref.getInstancia();

    public Localizacion ejecutarConsulta(String id) {
        return null;
    }
}
