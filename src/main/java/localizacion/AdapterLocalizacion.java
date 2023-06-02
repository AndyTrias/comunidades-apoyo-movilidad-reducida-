package localizacion;

import apiCalls.georef.responseClases.Ubicacion;

import java.io.IOException;

public interface AdapterLocalizacion {
    public Ubicacion getUbicacion(String tipoDeLocalizacion, String id) throws IOException;
}
