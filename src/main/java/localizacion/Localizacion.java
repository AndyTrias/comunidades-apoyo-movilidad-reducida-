package localizacion;

import apiCalls.georef.responseClases.Ubicacion;
import lombok.Setter;

public class Localizacion {
    @Setter
    AdapterLocalizacion adapter;

    public Ubicacion getUbicacion(String tipoDeLocalizacion, String id) throws Exception {
        return adapter.getUbicacion(tipoDeLocalizacion, id);
    }
}
