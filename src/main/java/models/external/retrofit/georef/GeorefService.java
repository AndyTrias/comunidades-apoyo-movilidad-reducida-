package models.external.georef;

import models.external.georef.responseClases.ListadoLocalidades;
import models.external.georef.responseClases.ListadoMunicipios;
import models.external.georef.responseClases.ListadoProvincias;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeorefService {
    @GET("provincias")
    Call<ListadoProvincias> provincias();

    @GET("provincias")
    Call<ListadoProvincias> provincias(@Query("id") int id, @Query("campos") String campos);

    @GET("municipios")
    Call<ListadoMunicipios> municipios(@Query("id") int id, @Query("campos") String campos);

    @GET("municipios")
    Call<ListadoMunicipios> municipios(@Query("provincia") String idProvincia);

    @GET("localidades")
    Call<ListadoLocalidades> localidades(@Query("id") long id, @Query("campos") String campos);

    @GET("localidades")
    Call<ListadoLocalidades> localidades(@Query("provincia") String idProvincia, @Query("municipio") String idMunicipio);
}
