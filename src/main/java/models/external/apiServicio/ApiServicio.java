package models.external.apiServicio;

import models.comunidades.Comunidad;
import models.comunidades.Fusion;
import models.external.apiServicio.responseClases.PayloadDTO;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import server.utils.PrettyProperties;

import java.io.IOException;
import java.util.List;

public class ApiServicio {
    private static ApiServicio instancia = null;
    private final Retrofit retrofit;

    private ApiServicio() {
        String urlAPI = PrettyProperties.getInstance().propertyFromName("API_SERVICIO");
        retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiServicio getInstancia() {
        if (instancia == null) {
            instancia = new ApiServicio();
        }
        return instancia;
    }

    public PayloadDTO comunidadesYFusiones(PayloadDTO payloadDTO) throws IOException {
        IApiServicio iApiServicio = this.retrofit.create(IApiServicio.class);
        Call<PayloadDTO> requestComunidadesYFusiones = iApiServicio.comunidadesYFusiones(payloadDTO);
        Response<PayloadDTO> responseComunidadesYFusiones = requestComunidadesYFusiones.execute();
        return responseComunidadesYFusiones.body();
    }
}
