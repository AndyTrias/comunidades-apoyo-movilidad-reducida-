package models.external.retrofit.apiServicio3;

import models.external.retrofit.ApiCaller;
import models.external.retrofit.apiServicio3.responseClases.PayloadServicio3DTO;
import retrofit2.Call;
import retrofit2.Response;
import server.utils.PrettyProperties;

import java.io.IOException;

public class ApiServicio3 extends ApiCaller {
    private static ApiServicio3 instancia = null;

    public static ApiServicio3 getInstancia() {
        if (instancia == null) {
            instancia = new ApiServicio3();
        }
        return instancia;
    }

    private ApiServicio3() {
        super(PrettyProperties.getInstance().propertyFromName("API_SERVICIO"));
    }

    public PayloadServicio3DTO rankingEntidades(PayloadServicio3DTO payloadServicio3DTO) throws IOException {
        IApiServicio3 iApiServicio3 = this.retrofit.create(IApiServicio3.class);
        Call<PayloadServicio3DTO> requestComunidadesYFusiones = iApiServicio3.rankingEntidades(payloadServicio3DTO);
        Response<PayloadServicio3DTO> responseComunidadesYFusiones = requestComunidadesYFusiones.execute();
        return responseComunidadesYFusiones.body();
    }
}
