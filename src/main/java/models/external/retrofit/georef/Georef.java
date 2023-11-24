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
        super(Config.getInstance().API_GEOREF);
    }

    public ListadoProvincias listadoProvincias(){
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoProvincias> requestProvinciasArg = georefService.provincias();
        try {
            Response<ListadoProvincias> responseProvinciasArg = requestProvinciasArg.execute();
            return responseProvinciasArg.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ListadoProvincias listadoProvincias(int idProvincia) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        String campos = "id,nombre";
        Call<ListadoProvincias> requestProvinciasArg = georefService.provincias(idProvincia, campos);
        Response<ListadoProvincias> responseProvinciasArg = requestProvinciasArg.execute();
        return responseProvinciasArg.body();
    }

    public ListadoMunicipios listadoMunicipios(String id) {
        try {
            GeorefService georefService = this.retrofit.create(GeorefService.class);
            Call<ListadoMunicipios> requestMunicipiosArg = georefService.municipios(id, 5000);
            Response<ListadoMunicipios> responseMunicipiosArg = requestMunicipiosArg.execute();
            return responseMunicipiosArg.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ListadoMunicipios listadoMunicipios(int idProvincia) {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        String campos = "id,nombre,provincia";
        Call<ListadoMunicipios> requestMunicipiosArg = georefService.municipios(idProvincia, campos);
        try {
            Response<ListadoMunicipios> responseMunicipiosArg = requestMunicipiosArg.execute();
            return responseMunicipiosArg.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ListadoLocalidades listadoLocalidades(String idProvincia, String idMunicipio) {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoLocalidades> requestLocalidadesArg = georefService.localidades(idProvincia, idMunicipio);
        try {
            Response<ListadoLocalidades> responseLocalidadesArg = requestLocalidadesArg.execute();
            return responseLocalidadesArg.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ListadoLocalidades listadoLocalidades(long id) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        String campos = "id,nombre,municipio,provincia";
        Call<ListadoLocalidades> requestLocalidadesArg = georefService.localidades(id, campos);
        Response<ListadoLocalidades> responseLocalidadesArg = requestLocalidadesArg.execute();
        return responseLocalidadesArg.body();
    }
}
