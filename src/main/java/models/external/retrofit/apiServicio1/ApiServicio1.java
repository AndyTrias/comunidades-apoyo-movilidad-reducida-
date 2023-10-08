package models.external.retrofit.apiServicio1;

import models.external.retrofit.apiServicio1.responseClases.PayloadDTO;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

public class Endpoints {
    private Retrofit retrofit;

    public 

    public PayloadDTO comunidadesYFusiones(PayloadDTO payloadDTO) throws IOException {
        IApiServicio1 iApiServicio1 = this.retrofit.create(IApiServicio1.class);
        Call<PayloadDTO> requestComunidadesYFusiones = iApiServicio1.comunidadesYFusiones(payloadDTO);
        Response<PayloadDTO> responseComunidadesYFusiones = requestComunidadesYFusiones.execute();
        return responseComunidadesYFusiones.body();
    }
}
