package apiCalls.georef;

import apiCalls.georef.responseClases.ListadoLocalidades;
import apiCalls.georef.responseClases.ListadoMunicipios;
import apiCalls.georef.responseClases.ListadoProvincias;
import apiCalls.georef.responseClases.Ubicacion;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GeorefService {
    @GET("{tipoDeLocalizacion}")
    Call<Ubicacion> tipoDeLocalizacion(
            @Path("tipoDeLocalizacion") String tipoDeLocalizacion,
            @Query("id") String id,
            @Query("campos") String campos);

}
