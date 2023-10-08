package models.external.apiServicio;

import models.external.apiServicio.responseClases.PayloadDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IApiServicio {
    @POST("/")
    Call<PayloadDTO> comunidadesYFusiones(@Body PayloadDTO payloadDTO);
}
