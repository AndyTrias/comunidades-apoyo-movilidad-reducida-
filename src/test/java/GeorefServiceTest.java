import apiCalls.georef.responseClases.ListadoProvincias;
import apiCalls.georef.responseClases.Ubicacion;
import apiCalls.georef.responseClases.UbicacionResponse;
import localizacion.AdapterLocalizacionGeorefApi;
import localizacion.Localizacion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeorefServiceTest {

    @Test
    public void testGetProvinciaId2() throws Exception {
        Localizacion localizacion = new Localizacion();
        AdapterLocalizacionGeorefApi adapterLocalizacionGeorefApi = new AdapterLocalizacionGeorefApi();
        localizacion.setAdapter(adapterLocalizacionGeorefApi);

        ListadoProvincias response = localizacion.getListadoProvincias();

        assertEquals("Buenos Aires", response.provincias.get(22).nombre);
    }

}
