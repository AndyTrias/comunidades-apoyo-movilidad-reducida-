package apiCalls.georef;

import apiCalls.georef.responseClases.ListadoLocalidades;
import apiCalls.georef.responseClases.ListadoMunicipios;
import apiCalls.georef.responseClases.ListadoProvincias;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GeorefService {
    @GET("provincias")
    Call<ListadoProvincias> provincias();

    @GET("provincias")
    Call<ListadoProvincias> provincias(@Query("id") int id);

    @GET("municipios")
    Call<ListadoMunicipios> municipios(@Query("id") int id);

    @GET("municipios")
    Call<ListadoMunicipios> municipios(@Query("provincia") String idProvincia);

    @GET("localidades")
    Call<ListadoLocalidades> localidades(@Query("id") long id);

    @GET("localidades")
    Call<ListadoLocalidades> localidades(@Query("provincia") String idProvincia, @Query("municipio") String idMunicipio);
}
