package models.external.georef;

import models.external.georef.responseClases.ListadoLocalidades;
import models.external.georef.responseClases.ListadoMunicipios;
import models.external.georef.responseClases.ListadoProvincias;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import server.utils.PrettyProperties;

import java.io.IOException;

public class ServicioGeoref {
    private static ServicioGeoref instancia = null;
    private final Retrofit retrofit;

    private ServicioGeoref() {
        String urlAPI = PrettyProperties.getInstance().propertyFromName("API_GEOREF");
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

    public ListadoProvincias listadoProvincias() throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoProvincias> requestProvinciasArg = georefService.provincias();
        Response<ListadoProvincias> responseProvinciasArg = requestProvinciasArg.execute();
        return responseProvinciasArg.body();
    }

    public ListadoProvincias listadoProvincias(int idProvincia) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        String campos = "id,nombre";
        Call<ListadoProvincias> requestProvinciasArg = georefService.provincias(idProvincia, campos);
        Response<ListadoProvincias> responseProvinciasArg = requestProvinciasArg.execute();
        return responseProvinciasArg.body();
    }

    public ListadoMunicipios listadoMunicipios(String id) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoMunicipios> requestMunicipiosArg = georefService.municipios(id);
        Response<ListadoMunicipios> responseMunicipiosArg = requestMunicipiosArg.execute();
        return responseMunicipiosArg.body();
    }

    public ListadoMunicipios listadoMunicipios(int idProvincia) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        String campos = "id,nombre,provincia";
        Call<ListadoMunicipios> requestMunicipiosArg = georefService.municipios(idProvincia, campos);
        Response<ListadoMunicipios> responseMunicipiosArg = requestMunicipiosArg.execute();
        return responseMunicipiosArg.body();
    }

    public ListadoLocalidades listadoLocalidades(String idProvincia, String idMunicipio) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoLocalidades> requestLocalidadesArg = georefService.localidades(idProvincia, idMunicipio);
        Response<ListadoLocalidades> responseLocalidadesArg = requestLocalidadesArg.execute();
        return responseLocalidadesArg.body();
    }

    public ListadoLocalidades listadoLocalidades(long id) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        String campos = "id,nombre,municipio,provincia";
        Call<ListadoLocalidades> requestLocalidadesArg = georefService.localidades(id, campos);
        Response<ListadoLocalidades> responseLocalidadesArg = requestLocalidadesArg.execute();
        return responseLocalidadesArg.body();
    }
}
