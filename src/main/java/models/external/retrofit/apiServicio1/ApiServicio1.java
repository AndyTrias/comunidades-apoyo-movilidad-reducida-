package models.external.retrofit.apiServicio1;

import models.external.retrofit.ApiCaller;
import models.external.retrofit.apiServicio1.responseClases.PayloadDTO;
import retrofit2.Call;
import retrofit2.Response;
import server.utils.PrettyProperties;

import java.io.IOException;

public class ApiServicio1 extends ApiCaller {
    private static ApiServicio1 instancia = null;

    public static ApiServicio1 getInstancia() {
        if (instancia == null) {
            instancia = new ApiServicio1();
        }
        return instancia;
    }

    private ApiServicio1() {
        super(PrettyProperties.getInstance().propertyFromName("API_SERVICIO1"));
    }

    public PayloadDTO comunidadesYFusiones(PayloadDTO payloadDTO) throws IOException {
        IApiServicio1 iApiServicio1 = this.retrofit.create(IApiServicio1.class);
        Call<PayloadDTO> requestComunidadesYFusiones = iApiServicio1.comunidadesYFusiones(payloadDTO);
        Response<PayloadDTO> responseComunidadesYFusiones = requestComunidadesYFusiones.execute();
        return responseComunidadesYFusiones.body();
    }
}
