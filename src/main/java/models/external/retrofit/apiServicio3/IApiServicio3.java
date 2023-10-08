package models.external.retrofit.apiServicio3;

import models.external.retrofit.apiServicio3.responseClases.PayloadServicio3DTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IApiServicio3 {
    @POST("/sort")
    Call<PayloadServicio3DTO> rankingEntidades(@Body PayloadServicio3DTO payloadServicio3DTO);
}
