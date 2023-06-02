package apiCalls.georef;

import apiCalls.georef.responseClases.ListadoLocalidades;
import apiCalls.georef.responseClases.ListadoMunicipios;
import apiCalls.georef.responseClases.ListadoProvincias;
import apiCalls.georef.responseClases.Ubicacion;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioGeoref {
    private static ServicioGeoref instancia = null;
    private static final String urlAPI = "https://apis.datos.gob.ar/georef/api/";
    private Retrofit retrofit;

    private ServicioGeoref() {
        retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioGeoref getInstancia() {
        if (instancia == null) {
            instancia = new ServicioGeoref();
        }
        return instancia;
    }

    public Ubicacion getUbicacion(String id, String tipoDeLocalizacion) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<Ubicacion> requestProvinciasArg = georefService.tipoDeLocalizacion(tipoDeLocalizacion, id, "id, nombre");
        Response<Ubicacion> responseProvinciasArg = requestProvinciasArg.execute();
        return responseProvinciasArg.body();
    }

}
