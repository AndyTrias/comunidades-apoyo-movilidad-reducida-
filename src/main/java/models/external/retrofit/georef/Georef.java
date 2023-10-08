package models.external.retrofit.georef;

import models.configs.Config;
import models.external.retrofit.ApiCaller;
import models.external.retrofit.georef.responseClases.ListadoLocalidades;
import models.external.retrofit.georef.responseClases.ListadoMunicipios;
import models.external.retrofit.georef.responseClases.ListadoProvincias;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

public class Georef extends ApiCaller {
    private static Georef instancia = null;

    public static Georef getInstancia() {
        if (instancia == null) {
            instancia = new Georef();
        }
        return instancia;
    }

    private Georef() {
        super(Config.API_GEOREF);
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
