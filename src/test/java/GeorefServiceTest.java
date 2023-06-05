import apiCalls.georef.responseClases.ListadoMunicipios;
import apiCalls.georef.responseClases.ListadoProvincias;
import localizacion.AdapterLocalizacionGeorefApi;
import localizacion.Localizacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeorefServiceTest {
    private Localizacion localizacion;
    private AdapterLocalizacionGeorefApi adapterLocalizacionGeorefApi;

    @BeforeEach
    public void setUp() throws Exception {
        this.localizacion = new Localizacion();
        this.adapterLocalizacionGeorefApi = new AdapterLocalizacionGeorefApi();
        this.localizacion.setAdapter(adapterLocalizacionGeorefApi);
    }

    @Test
    public void testGetProvincias() throws Exception {

        ListadoProvincias provincias = localizacion.getListadoProvincias();

        for (int i = 0; i < provincias.provincias.size(); i++) {
            System.out.println(provincias.provincias.get(i).id + " " + provincias.provincias.get(i).nombre);
        }

        assertEquals("Buenos Aires", provincias.provincias.get(22).nombre);
    }

    @Test
    public void testGetMunicipiosDeBuenosAires() throws Exception {

        ListadoMunicipios municipios = localizacion.getMunicipiosDeProvincia("6");

        for (int i = 0; i < municipios.municipios.size(); i++) {
            System.out.println(municipios.municipios.get(i).id + " " + municipios.municipios.get(i).nombre);
        }

        assertEquals("Bahía Blanca", municipios.municipios.get(0).nombre);
    }

    @Test
    public void testSetLocalizacion() throws Exception {
        this.localizacion.setLocalidad(6056010001L);

        assertEquals("BAHIA BLANCA", this.localizacion.getUbicacion().getLocalidad().nombre);
    }

}
