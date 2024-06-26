package com.tlu.edu.vn.ht63.btl_nhom10.Model.Api;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DistanceMatrixApi {
    @GET("maps/api/distancematrix/json")
    Call<DistanceResponse> getDistance(
            @Query("origins") String origins,
            @Query("destinations") String destinations,
            @Query("key") String apiKey,
            @Query("mode") String mode
    );
}
