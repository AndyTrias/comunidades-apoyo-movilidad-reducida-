package models.external.retrofit.apiServicio1;

import models.external.retrofit.apiServicio1.responseClases.PayloadDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IApiServicio1 {
    @POST("/")
    Call<PayloadDTO> comunidadesYFusiones(@Body PayloadDTO payloadDTO);
}
